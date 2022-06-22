package com.ecotrade.mswebsocket.service;

import com.ecotrade.mswebsocket.config.security.SecurityUtils;
import com.ecotrade.mswebsocket.domain.Message;
import com.ecotrade.mswebsocket.domain.dto.AdvertisementSimpleDTO;
import com.ecotrade.mswebsocket.domain.dto.MessageDTO;
import com.ecotrade.mswebsocket.domain.dto.MessageSimpleDTO;
import com.ecotrade.mswebsocket.domain.dto.OfferPhotosDTO;
import com.ecotrade.mswebsocket.domain.dto.UserDetailsDTO;
import com.ecotrade.mswebsocket.domain.enuns.MessageStatus;
import com.ecotrade.mswebsocket.repository.MessageRepository;
import com.ecotrade.mswebsocket.service.feign.AdvertisementFeign;
import com.ecotrade.mswebsocket.service.feign.OfferFeign;
import com.ecotrade.mswebsocket.service.interfaces.SerializationUtils;
import com.ecotrade.mswebsocket.service.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService implements SerializationUtils {

    private final String BASIC_NOSQL_ADVERTISEMENT_KEY = "key_advertisement_";
    private final String BASIC_NOSQL_OFFER_KEY = "key_offer_";
    private final MessageRepository repository;
    private final NoSqlService noSqlService;
    private final AdvertisementFeign advertisementFeign;
    private final OfferFeign offerFeign;
    private final MessageMapper mapper;

    @Transactional
    public void sendMenssage(Long advertisementId, Long offerId, MessageDTO messageDto) {
        AdvertisementSimpleDTO advertisement = getAdvertisementById(advertisementId);
        OfferPhotosDTO byId = getOfferById(offerId);
        repository.save(getMessage(messageDto, advertisement, byId));
    }

    private OfferPhotosDTO getOfferById(Long offerId) {
        String basicKey = String.format("%s%s", BASIC_NOSQL_OFFER_KEY, offerId);
        OfferPhotosDTO cachedValue;
        cachedValue = getOfferFromRedis(offerId);
        if (cachedValue == null) {
            OfferPhotosDTO noCachedValue = offerFeign.findById(offerId);
            log.info("Setting Cache in redis key: {}", basicKey);
            setOfferInRedis(basicKey, noCachedValue);
            return noCachedValue;
        }
        return cachedValue;
    }

    private void setOfferInRedis(String basicKey, OfferPhotosDTO noCachedValue) {
        noSqlService.setValue(basicKey, serialize(noCachedValue));
    }

    private OfferPhotosDTO getOfferFromRedis(Long offerId) {
        return deserializar((String) noSqlService.getValue(String.format("%s%s", BASIC_NOSQL_OFFER_KEY, offerId)), OfferPhotosDTO.class);
    }

    private AdvertisementSimpleDTO getAdvertisementById(Long advertisementId) {
        String basicKey = String.format("%s%s", BASIC_NOSQL_ADVERTISEMENT_KEY, advertisementId);
        AdvertisementSimpleDTO cachedValue;
        cachedValue = getAdvertisementFromRedis(basicKey);
        if (cachedValue == null) {
            AdvertisementSimpleDTO noCached = advertisementFeign.findById(advertisementId);
            log.info("Setting Cache in redis key: {}", basicKey);
            setAdvertisementInRedis(basicKey, noCached);
            return noCached;
        }
        return cachedValue;
    }

    private void setAdvertisementInRedis(String basicKey, AdvertisementSimpleDTO noCached) {
        noSqlService.setValue(basicKey, serialize(noCached), TimeUnit.DAYS, 1);
    }

    private AdvertisementSimpleDTO getAdvertisementFromRedis(String basicKey) {
        return deserializar((String) noSqlService.getValue(basicKey), AdvertisementSimpleDTO.class);
    }

    private Message getMessage(MessageDTO messageDto, AdvertisementSimpleDTO advertisement, OfferPhotosDTO offer) {
        Message message = mapper.toEntity(messageDto);
        message.setSendAt(LocalDateTime.now());
        message.setAdvertisementId(advertisement.getId());
        message.setStatus(MessageStatus.SENT);
        message.setOfferId(offer.getId());
        setSourceAndTarget(advertisement, message, offer);
        return message;
    }

    private void setSourceAndTarget(AdvertisementSimpleDTO advertisement, Message message, OfferPhotosDTO byId) {
        UserDetailsDTO detailsDTO = SecurityUtils.getAuthUser();
        if (advertisement.getAdvertiser().getId().equals(detailsDTO.getId())) {
            message.setUserSource(detailsDTO.getId());
            message.setUserTarget(byId.getOfferer().getId());
            return;
        }
        message.setUserSource(byId.getOfferer().getId());
        message.setUserTarget(detailsDTO.getId());
    }

    public List<MessageSimpleDTO> getAllMessage(Long advertisementId, Long offerId) {
        List<Message> messages = repository.findMessages(advertisementId, offerId, SecurityUtils.getAuthUser().getId());
        List<MessageSimpleDTO> response = messages.stream().map(mapper::toSimpleDto).collect(Collectors.toList());
        alterMessageStatus(messages, MessageStatus.READ);
        return response;
    }

    @Transactional
    public void alterMessageStatus(List<Message> messages, MessageStatus status) {
        messages.forEach(msg -> msg.setStatus(status));
        repository.saveAll(messages);
    }
}
