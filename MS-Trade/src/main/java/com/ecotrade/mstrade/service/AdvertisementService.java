package com.ecotrade.mstrade.service;

import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.config.security.SecurityUtils;
import com.ecotrade.mstrade.domain.Advertisement;
import com.ecotrade.mstrade.domain.Advertisement_;
import com.ecotrade.mstrade.domain.Offer;
import com.ecotrade.mstrade.domain.Offer_;
import com.ecotrade.mstrade.domain.UploadFile;
import com.ecotrade.mstrade.domain.User_;
import com.ecotrade.mstrade.domain.dto.AdvertisementDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementFilterDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementSimpleDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementSingleOfferDTO;
import com.ecotrade.mstrade.domain.dto.OfferPhotosDTO;
import com.ecotrade.mstrade.domain.enuns.AdvertisementChangeStatus;
import com.ecotrade.mstrade.domain.enuns.AdvertisementStatus;
import com.ecotrade.mstrade.domain.enuns.OfferStatus;
import com.ecotrade.mstrade.repository.AdvertisementRepository;
import com.ecotrade.mstrade.repository.predicate.EcopointsPredicate;
import com.ecotrade.mstrade.service.interfaces.BasicService;
import com.ecotrade.mstrade.service.interfaces.ReliabilityBO;
import com.ecotrade.mstrade.service.mapper.AdvertisementMapper;
import com.ecotrade.mstrade.service.mapper.OfferMapper;
import com.ecotrade.mstrade.service.specification.BaseSpecs;
import com.ecotrade.mstrade.service.utils.FileUtils;
import com.ecotrade.mstrade.service.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertisementService implements BasicService, BaseSpecs<Advertisement>, ReliabilityBO {

    private final BucketService bucketService;
    private final OfferService offerService;
    private final UserService userService;
    private final AdvertisementMapper mapper;
    private final OfferMapper offerMapper;
    private final AdvertisementRepository repository;
    private final Integer MAX_FILE_QUANTITY = 5;

    @Transactional
    public ResponseDTO create(AdvertisementDTO dto, MultipartFile[] files) {
        dto.setId(null);
        Advertisement advertisement = mapper.toEntity(dto);
        validateFiles(files, advertisement);
        advertisement.setStatus(AdvertisementStatus.OPEN);
        advertisement.setAdvertiser(userService.getAuthUser());
        repository.save(advertisement);
        return getSuccess("advertisement.create.success");
    }

    @Transactional
    public ResponseDTO delete(Long id) {
        Advertisement advertisement = findById(id);
        repository.delete(advertisement);
        return getSuccess("advertisement.delete.success");
    }

    public Page<AdvertisementSimpleDTO> filter(Pageable pageable, AdvertisementFilterDTO filter) {
        Specification<Advertisement> specs = buildSpecAnd(byLike(Advertisement_.description, filter.getDescription()));
        specs = buildSpecAnd(specs, byLike(Advertisement_.product, filter.getProduct()));
        specs = buildSpecAnd(specs, byLike(Advertisement_.title, filter.getTitle()));
        specs = buildSpecAnd(specs, byLike(Advertisement_.tradePossibility, filter.getTradePossibility()));
        specs = buildSpecAnd(specs, byLike(Advertisement_.category, filter.getCategory()));
        specs = buildSpecAnd(specs, byEquals(Advertisement_.status, AdvertisementStatus.OPEN));
        return repository.findAll(specs, pageable).map(mapper::toSimpleDto);
    }

    public Advertisement findById(Long id) {
        return repository.findById(id).orElseThrow(() -> getException("advertisement.not.found"));
    }

    public List<AdvertisementSimpleDTO> findMyAdvertisement() {
        Specification<Advertisement> specs = buildSpecAnd(byEqualsJoinSpec(Advertisement_.advertiser, User_.id, SecurityUtils.getAuthUser().getId()));
        return repository.findAll(specs).stream().map(mapper::toSimpleDto).collect(Collectors.toList());
    }

    public AdvertisementSimpleDTO getById(Long id) {
        AdvertisementSimpleDTO advertisementSimpleDTO = mapper.toSimpleDto(findById(id));
        advertisementSimpleDTO.getAdvertiser().setReliability(userService.getReliabilityOfUser(advertisementSimpleDTO.getAdvertiser().getId()));
        return advertisementSimpleDTO;
    }

    @Override
    public BusinessException getException(String message) {
        return new BusinessException(MessageUtils.ADVERTISEMENT_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.ADVERTISEMENT_SUCCESS, message);
    }

    public Boolean isAdvertisementOwner(String email, Long id) {
        return repository.existsAdvertisementByAdvertiser_EmailAndId(email, id);
    }

    @Transactional
    public ResponseDTO update(AdvertisementDTO dto, MultipartFile[] files) {
        Advertisement advertisement = mapper.toEntity(dto);
        if (files != null) {
            validateFiles(files, advertisement);
        }
        advertisement.setStatus(AdvertisementStatus.OPEN);
        advertisement.setAdvertiser(userService.getAuthUser());
        repository.save(advertisement);
        return getSuccess("advertisement.edited.success");
    }

    private void validateFiles(MultipartFile[] files, Advertisement advertisement) {
        advertisement.setPhotos(new ArrayList<>());
        if (files.length > MAX_FILE_QUANTITY || CollectionUtils.isEmpty(Arrays.asList(files))) {
            throw new BusinessException("file.exception", "file.quantity.invalid");
        }
        Arrays.stream(files).forEach(file -> {
            if (!FileUtils.haveImageExtentions(file)) {
                throw new BusinessException("file.exception", "file.extension.image.invalid");
            }
            UploadFile upload = bucketService.upload(file);
            advertisement.getPhotos().add(upload);
        });
    }

    public List<AdvertisementSimpleDTO> findAllBySearch(String title) {
        Specification<Advertisement> specs = buildSpecAnd(byLike(Advertisement_.title, title));
        return repository.findAll(specs).stream().map(mapper::toSimpleDto).collect(Collectors.toList());
    }

    @Transactional
    public ResponseDTO confirmRecive(Long id, Long offerId) {
        Advertisement advertisement = findById(id);
        if (!AdvertisementStatus.ACCEPTED.equals(advertisement.getStatus())) {
            throw getException("advertisement.not.accepted");
        }
        advertisement.setStatus(AdvertisementStatus.RECIVED);
        Offer offer = offerService.findById(offerId);
        offer.setStatus(OfferStatus.RECIVED);
        advertisement.setAcceptedOffer(offer);
        return new ResponseDTO("trade.success", "trade.success");
    }

    @Transactional
    public ResponseDTO changeStatusOfOffer(Long id, Long offerId, AdvertisementChangeStatus status) {
        Advertisement advertisement = findById(id);
        if (!AdvertisementStatus.OPEN.equals(advertisement.getStatus())) {
            throw getException("advertisement.not.open");
        }
        return verifyChangeType(status, advertisement, offerId);
    }

    private ResponseDTO verifyChangeType(AdvertisementChangeStatus status, Advertisement advertisement, Long offerId) {
        if (AdvertisementChangeStatus.REJECTED.equals(status)) {
            advertisement.getOffers().forEach(offer -> offer.setStatus(OfferStatus.REJECTED));
            repository.save(advertisement);
            return getSuccess("trade.success");
        }
        advertisement.setStatus(AdvertisementStatus.ACCEPTED);
        advertisement.setAcceptedOffer(offerService.findById(offerId));
        advertisement.getOffers().forEach(offer -> {
            if (!offer.getId().equals(offerId)) {
                offer.setStatus(OfferStatus.REJECTED);
                return;
            }
            offer.setStatus(OfferStatus.APPROVED);
        });
        repository.save(advertisement);
        return getSuccess("trade.success");
    }

    public AdvertisementSingleOfferDTO getOfferAnalises(Long id, Long offerId) {
        Advertisement advertisement = findById(id);
        AdvertisementSingleOfferDTO advertisementDto = mapper.toSingleDto(advertisement);
        advertisementDto.getAdvertiser().setReliability(userService.getReliabilityOfUser(advertisement.getAdvertiser().getId()));
        OfferPhotosDTO offer = offerMapper.toPhotoDto(offerService.findById(offerId));
        offer.getOfferer().setReliability(userService.getReliabilityOfUser(offer.getOfferer().getId()));
        advertisementDto.setOffer(offer);
        return advertisementDto;
    }
}
