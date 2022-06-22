package com.ecotrade.mstrade.service;

import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.domain.Organization;
import com.ecotrade.mstrade.domain.dto.OrganizationDTO;
import com.ecotrade.mstrade.repository.OrganizationRepository;
import com.ecotrade.mstrade.service.interfaces.BasicService;
import com.ecotrade.mstrade.service.mapper.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService implements BasicService {

    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;

    public List<OrganizationDTO> findAllOrganization() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Organization getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("organization.exception", "organization.not.found"));
    }

    @Override
    public BusinessException getException(String message) {
        return null;
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return null;
    }
}
