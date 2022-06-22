package com.ecotrade.mswebsocket.service.feign;

import com.ecotrade.mswebsocket.domain.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "debug.feign", url = "${ecotrade.trade.url}")
public interface DebugFeign {

    @GetMapping("/public/debug")
    String getUrl();


    @GetMapping("/user/by-id/{id}")
    UserDetailsDTO getUserById(@PathVariable Long id);

}
