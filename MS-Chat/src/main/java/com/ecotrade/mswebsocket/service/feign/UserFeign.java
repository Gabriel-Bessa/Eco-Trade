package com.ecotrade.mswebsocket.service.feign;

import com.ecotrade.mswebsocket.domain.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user.feign", url = "${ecotrade.trade.url}")
public interface UserFeign {

    @GetMapping("/user/by-id/{id}")
    UserDetailsDTO getUserByid(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id
    );

}
