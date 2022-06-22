package com.ecotrade.mswebsocket.config.security.evaluators;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Configuration
@RequiredArgsConstructor
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication auth, Object id, Object permissionType) {
        return true;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable id, String permissionType, Object permission) {
        //TODO: Implements permissions
        return true;
    }
}
