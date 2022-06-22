package com.ecotrade.mstrade.service.mapper;

import com.ecotrade.mstrade.domain.Offer;
import com.ecotrade.mstrade.domain.UploadFile;
import com.ecotrade.mstrade.domain.dto.OfferAdvertisementDTO;
import com.ecotrade.mstrade.domain.dto.OfferDTO;
import com.ecotrade.mstrade.domain.dto.OfferPhotosDTO;
import com.ecotrade.mstrade.domain.dto.OfferSimpleDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    Offer toEntity(OfferSimpleDTO dto);

    OfferDTO toDto(Offer entity);

    @Mapping(target = "advertisementId", source = "advertisement.id")
    OfferPhotosDTO toPhotoDto(Offer entity);

    OfferSimpleDTO toSimpleDto(Offer entity);

    @Mapping(source = "advertisement.title", target = "advertisement.name")
    OfferAdvertisementDTO toAdvertisementDto(Offer entity);

    @AfterMapping
    default void addPhotosUrl(@MappingTarget OfferDTO dto, Offer entity) {
        if (!CollectionUtils.isEmpty(entity.getPhotos())) {
            dto.setPhotosUrl(entity.getPhotos().stream().map(UploadFile::getUrl).collect(Collectors.toList()));
        }
    }

    @AfterMapping
    default void addPhotosUrl(@MappingTarget OfferPhotosDTO dto, Offer entity) {
        if (!CollectionUtils.isEmpty(entity.getPhotos())) {
            dto.setPhotosUrl(entity.getPhotos().stream().map(UploadFile::getUrl).collect(Collectors.toList()));
        }
        dto.getOfferer().setUrl(entity.getOfferer().getPhotos().get(0).getUrl());
    }

    @AfterMapping
    default void addPhotosUrl(@MappingTarget OfferAdvertisementDTO dto, Offer entity) {
        if (!CollectionUtils.isEmpty(entity.getPhotos())) {
            dto.setPhotosUrl(entity.getPhotos().stream().map(UploadFile::getUrl).collect(Collectors.toList()));
        }
    }
}
