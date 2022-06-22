package com.ecotrade.mstrade.service;

import com.ecotrade.mstrade.config.errors.BusinessException;
import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.domain.Role;
import com.ecotrade.mstrade.repository.RoleRepository;
import com.ecotrade.mstrade.service.interfaces.BasicService;
import com.ecotrade.mstrade.service.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService implements BasicService {

    private RoleRepository repository;

    public Role findRoleByName(String name) {
        return repository.findByName(name).orElseThrow(() -> getException("role.not.found"));
    }

    @Override
    public BusinessException getException(String message) {
        return new BusinessException(MessageUtils.ROLE_EXCEPTION, message);
    }

    @Override
    public ResponseDTO getSuccess(String message) {
        return new ResponseDTO(MessageUtils.ROLE_SUCCESS, message);
    }

    @Transactional
    public Role saveRole(Role role) {
        return repository.save(role);
    }
}
