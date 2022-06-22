package com.ecotrade.mstrade.config.security.evaluators;

import com.ecotrade.mstrade.service.AdvertisementService;
import com.ecotrade.mstrade.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@RequiredArgsConstructor
public class MethodSecurityConfigSecurity extends GlobalMethodSecurityConfiguration {

    private final AdvertisementService advertisementService;
    private final OfferService offerService;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new PermissionEvaluator(advertisementService, offerService));
        return expressionHandler;
    }
}
