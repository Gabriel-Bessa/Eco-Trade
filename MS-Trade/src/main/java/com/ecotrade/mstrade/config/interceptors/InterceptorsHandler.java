package com.ecotrade.mstrade.config.interceptors;

import com.ecotrade.mstrade.config.PosResquestHandle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
@RequiredArgsConstructor
public class InterceptorsHandler extends WebMvcConfigurerAdapter {

    private final PosResquestHandle handle;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handle);
    }

}
