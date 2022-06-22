package com.ecotrade.mswebsocket.controller;

import com.ecotrade.mswebsocket.config.security.credentials.CredentialsService;
import com.ecotrade.mswebsocket.domain.dto.UserDetailsDTO;
import com.ecotrade.mswebsocket.service.feign.DebugFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DebugController {
    private final DebugFeign feign;

    @GetMapping("/debug")
    @PreAuthorize("permitAll()")
    public String host() {
        UserDetailsDTO userById = feign.getUserById(999999L);
        return "Debug";
    }
}
