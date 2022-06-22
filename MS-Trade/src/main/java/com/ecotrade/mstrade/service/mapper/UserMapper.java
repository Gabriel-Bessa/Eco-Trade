package com.ecotrade.mstrade.service.mapper;

import com.ecotrade.mstrade.domain.User;
import com.ecotrade.mstrade.domain.dto.UserCreateDTO;
import com.ecotrade.mstrade.domain.dto.UserDTO;
import com.ecotrade.mstrade.domain.dto.UserDetailsDTO;
import com.ecotrade.mstrade.domain.dto.UserFullNoPasswordDTO;
import com.ecotrade.mstrade.domain.dto.UserSimpleDTO;
import com.ecotrade.mstrade.domain.dto.UserUpdateDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateDTO dto);

    User toEntity(UserUpdateDTO dto);

    UserSimpleDTO toSimpleDto(User entity);

    UserDTO toDto(User entity);

    UserFullNoPasswordDTO toNoPasswordDTO(User entity);

    @Mapping(source = "email", target = "username")
    UserDetailsDTO toUserDetailsDto(User entity);

    @AfterMapping
    default void addPhotosUrl(@MappingTarget UserDTO dto, User entity) {
        if (!CollectionUtils.isEmpty(entity.getPhotos())) {
            dto.setUrl(entity.getPhotos().get(0).getUrl());
        }
    }

    @AfterMapping
    default void addPhotosUrl(@MappingTarget UserFullNoPasswordDTO dto, User entity) {
        if (!CollectionUtils.isEmpty(entity.getPhotos())) {
            dto.setUrl(entity.getPhotos().get(0).getUrl());
        }
    }
}
