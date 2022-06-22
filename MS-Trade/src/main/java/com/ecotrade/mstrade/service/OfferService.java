package com.ecotrade.mstrade.service;

import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.config.security.SecurityUtils;
import com.ecotrade.mstrade.domain.Advertisement;
import com.ecotrade.mstrade.domain.Offer;
import com.ecotrade.mstrade.domain.UploadFile;
import com.ecotrade.mstrade.domain.dto.OfferAdvertisementDTO;
import com.ecotrade.mstrade.domain.dto.OfferDTO;
import com.ecotrade.mstrade.domain.dto.OfferPhotosDTO;
import com.ecotrade.mstrade.domain.dto.OfferSimpleDTO;
import com.ecotrade.mstrade.domain.enuns.OfferStatus;
import com.ecotrade.mstrade.repository.OfferRepository;
import com.ecotrade.mstrade.service.interfaces.BasicService;
import com.ecotrade.mstrade.service.interfaces.ReliabilityBO;
import com.ecotrade.mstrade.service.mapper.OfferMapper;
import com.ecotrade.mstrade.service.specification.BaseSpecs;
import com.ecotrade.mstrade.service.utils.FileUtils;
import com.ecotrade.mstrade.service.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OfferService implements BasicService, BaseSpecs<Offer>, ReliabilityBO {

    private final UserService userService;
    private final BucketService bucketService;
    private final AdvertisementMiddlerService advertisementService;
    private final OfferRepository repository;
    private final OfferMapper mapper;
    private final Integer MAX_FILE_QUANTITY = 5;

    @Transactional
    public ResponseDTO create(OfferSimpleDTO dto, MultipartFile[] files) {
        Advertisement advertisement = advertisementService.findById(dto.getAdvertisementId());
        if (advertisement.getAdvertiser().getEmail().equals(SecurityUtils.getAuthEmail())) {
            throw getException("offer.is.yours");
        }
        Offer offer = mapper.toEntity(dto);
        offer.setStatus(OfferStatus.OPEN);
        offer.setOfferer(userService.getAuthUser());
        offer.setAdvertisement(advertisement);
        validateFiles(files, offer);
        repository.save(offer);
        return getSuccess("offer.create.success");
    }

    @Transactional
    public ResponseDTO deleteById(Long id) {
        repository.deleteById(id);
        return getSuccess("offer.delete.success");
    }

    public List<OfferDTO> getAllByAdvertisementId(Long id) {
        List<OfferDTO> offers = repository.findAllByAdvertisement_Id(id).stream().map(mapper::toDto).collect(Collectors.toList());
        return offers;
    }

    public List<OfferAdvertisementDTO> getAllByUser() {
        return repository.findAllByEmailOrAdvertisementEmail(SecurityUtils.getAuthEmail()).stream().map(mapper::toAdvertisementDto).collect(Collectors.toList());
    }

    public OfferPhotosDTO getById(Long id) {
        OfferPhotosDTO offerPhotosDTO = mapper.toPhotoDto(findById(id));
        offerPhotosDTO.getOfferer().setReliability(calcReliability(advertisementService.getReliabilityOfAuthUser()));
        return offerPhotosDTO;
    }

    @Transactional
    public ResponseDTO update(OfferSimpleDTO dto, MultipartFile[] files) {
        Offer oldOffer = findById(dto.getId());
        if (!OfferStatus.OPEN.equals(oldOffer.getStatus())) {
            throw getException("offer.not.open");
        }
        Offer offer = mapper.toEntity(dto);
        offer.setOfferer(userService.getAuthUser());
        offer.setAdvertisement(oldOffer.getAdvertisement());
        offer.setStatus(oldOffer.getStatus());
        validateFiles(files, offer);
        repository.save(offer);
        return getSuccess("offer.edited.success");
    }

    public Offer findById(Long id) {
        return repository.findById(id).orElseThrow(() -> getException("offer.not.found"));
    }

    public Offer findByIdAndAdvertisementId(Long id, Long advertisementId) {
        return repository.findByIdAndAdvertisementId(id, advertisementId).orElseThrow(() -> getException("offer.not.found"));
    }

    @Override
    public BusinessException getException(String message) {
        return new BusinessException(MessageUtils.OFFER_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.OFFER_SUCCESS, message);
    }

    public Boolean isOwner(String email, Long id) {
        return repository.existsByIdAndOffererEmail(id, email);
    }

    private void validateFiles(MultipartFile[] files, Offer offer) {
        offer.setPhotos(new ArrayList<>());
        if (files.length > MAX_FILE_QUANTITY) {
            throw new BusinessException("file.exception", "file.quantity.invalid");
        }
        Arrays.stream(files).forEach(file -> {
            if (!FileUtils.haveImageExtentions(file)) {
                throw new BusinessException("file.exception", "file.extension.image.invalid");
            }
            UploadFile upload = bucketService.upload(file);
            offer.getPhotos().add(upload);
        });
    }
}
