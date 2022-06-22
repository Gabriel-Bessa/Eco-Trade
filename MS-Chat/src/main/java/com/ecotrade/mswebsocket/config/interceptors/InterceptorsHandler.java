package com.ecotrade.mswebsocket.config.interceptors;

import com.ecotrade.mswebsocket.config.PosResquestHandle;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
@Profile({"dev"})
@RequiredArgsConstructor
public class InterceptorsHandler extends WebMvcConfigurerAdapter {

    private final PosResquestHandle handle;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handle);
    }

}
