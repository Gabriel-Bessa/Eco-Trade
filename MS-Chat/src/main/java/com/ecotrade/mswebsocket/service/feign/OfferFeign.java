package com.ecotrade.mswebsocket.service.feign;

import com.ecotrade.mswebsocket.domain.dto.AdvertisementSimpleDTO;
import com.ecotrade.mswebsocket.domain.dto.OfferPhotosDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "offer.feign", url = "${ecotrade.trade.url}")
public interface OfferFeign {

    @GetMapping("/v1-internal/offer/{id}")
    OfferPhotosDTO findById(@PathVariable Long id);
}
