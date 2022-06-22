package com.ecotrade.mstrade.service.mapper;

import com.ecotrade.mstrade.domain.Advertisement;
import com.ecotrade.mstrade.domain.UploadFile;
import com.ecotrade.mstrade.domain.dto.AdvertisementDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementSimpleDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementSingleOfferDTO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {
    String delimiter = ";";
    Advertisement toEntity(AdvertisementDTO dto);
    AdvertisementDTO toDto(Advertisement entity);
    AdvertisementSimpleDTO toSimpleDto(Advertisement entity);
    AdvertisementSingleOfferDTO toSingleDto(Advertisement entity);

    @AfterMapping
    default void addCategories(@MappingTarget Advertisement entity, AdvertisementDTO dto) {
        entity.setCategory(String.join(delimiter, dto.getCategories()));
    }

    @AfterMapping
    default void transformCategories(@MappingTarget AdvertisementSimpleDTO dto, Advertisement entity) {
        if (StringUtils.isNotEmpty(entity.getCategory())) {
            dto.setCategories(Arrays.asList(entity.getCategory().split(delimiter)));
        }
        if (!CollectionUtils.isEmpty(entity.getPhotos())) {
            dto.setPhotosUrl(entity.getPhotos().stream().map(UploadFile::getUrl).collect(Collectors.toList()));
        }
        dto.getAdvertiser().setUrl(entity.getAdvertiser().getPhotos().get(0).getUrl());
    }

    @AfterMapping
    default void transformCategories(@MappingTarget AdvertisementSingleOfferDTO dto, Advertisement entity) {
        if (StringUtils.isNotEmpty(entity.getCategory())) {
            dto.setCategories(Arrays.asList(entity.getCategory().split(delimiter)));
        }
        if (!CollectionUtils.isEmpty(entity.getPhotos())) {
            dto.setPhotosUrl(entity.getPhotos().stream().map(UploadFile::getUrl).collect(Collectors.toList()));
        }
    }

    @AfterMapping
    default void transformCategories(@MappingTarget AdvertisementDTO dto, Advertisement entity) {
        if (StringUtils.isNotEmpty(entity.getCategory())) {
            dto.setCategories(Arrays.asList(entity.getCategory().split(delimiter)));
        }
    }
}
