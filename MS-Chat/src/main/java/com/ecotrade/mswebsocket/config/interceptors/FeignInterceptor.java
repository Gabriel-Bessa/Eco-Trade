package com.ecotrade.mswebsocket.config.interceptors;

import com.ecotrade.mswebsocket.config.security.credentials.CredentialsService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FeignInterceptor implements RequestInterceptor {

    private static final List<String> internalPossibilities = Arrays.asList("/v1-internal");
    private final CredentialsService credentialsService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String targetUrl = requestTemplate.url();
        internalPossibilities.stream().forEach(it -> {
            if (targetUrl.startsWith(it)) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, Arrays.asList(credentialsService.getHostToHostCreddential()));
            }
        });
    }
}
