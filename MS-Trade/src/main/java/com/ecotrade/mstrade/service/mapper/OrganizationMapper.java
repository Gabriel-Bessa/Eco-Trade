package com.ecotrade.mstrade.service.mapper;

import com.ecotrade.mstrade.domain.Organization;
import com.ecotrade.mstrade.domain.dto.OrganizationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrganizationMapper {

    OrganizationDTO toDto(Organization entity);

}
