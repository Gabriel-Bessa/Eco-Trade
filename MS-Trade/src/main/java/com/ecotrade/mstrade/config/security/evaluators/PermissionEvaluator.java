package com.ecotrade.mstrade.config.security.evaluators;

import com.ecotrade.mstrade.config.security.SecurityUtils;
import com.ecotrade.mstrade.service.AdvertisementService;
import com.ecotrade.mstrade.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;

@Configuration
@RequiredArgsConstructor
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    private final AdvertisementService advertisementService;
    private final OfferService offerService;

    @Override
    public boolean hasPermission(Authentication auth, Object id, Object permissionType) {
        if (SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return false;
        }
        switch (HasPermitionTypes.valueOf((String) permissionType)) {
            case ADVERTISEMENT:
                return advertisementService.isAdvertisementOwner(SecurityUtils.getAuthEmail(), (Long) id);
            case OFFER:
                return offerService.isOwner(SecurityUtils.getAuthEmail(), (Long) id);
            default:
                return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable id, String permissionType, Object permission) {
        switch (HasPermitionTypes.valueOf(permissionType)) {
            case ADVERTISEMENT:
                return advertisementService.isAdvertisementOwner(SecurityUtils.getAuthEmail(), (Long) id);
            case OFFER:
                return offerService.isOwner(SecurityUtils.getAuthEmail(), (Long) id);
            default:
                return false;
        }
    }
}
