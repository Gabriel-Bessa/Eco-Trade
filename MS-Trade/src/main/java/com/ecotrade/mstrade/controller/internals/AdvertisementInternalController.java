package com.ecotrade.mstrade.controller.internals;

import com.ecotrade.mstrade.domain.Advertisement;
import com.ecotrade.mstrade.domain.dto.AdvertisementSimpleDTO;
import com.ecotrade.mstrade.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1-internal/advertisement")
public class AdvertisementInternalController {

    private final AdvertisementService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_HOST_TO_HOST')")
    public AdvertisementSimpleDTO findById(@PathVariable Long id) {
        log.info("REST request to find internal advertisement by id: {}", id);
        return service.getById(id);
    }
}
