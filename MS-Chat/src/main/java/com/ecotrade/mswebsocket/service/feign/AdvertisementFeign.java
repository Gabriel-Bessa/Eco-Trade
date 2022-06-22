package com.ecotrade.mswebsocket.service.feign;

import com.ecotrade.mswebsocket.domain.dto.AdvertisementSimpleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "advertisement.feign", url = "${ecotrade.trade.url}")
public interface AdvertisementFeign {

    @GetMapping("/v1-internal/advertisement/{id}")
    AdvertisementSimpleDTO findById(@PathVariable Long id);
}
