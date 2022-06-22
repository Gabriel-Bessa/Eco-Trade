package com.ecotrade.mstrade.controller.internals;

import com.ecotrade.mstrade.domain.dto.OfferDTO;
import com.ecotrade.mstrade.domain.dto.OfferPhotosDTO;
import com.ecotrade.mstrade.service.OfferService;
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
@RequestMapping("/v1-internal/offer")
public class OfferInternalController {

    private final OfferService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_HOST_TO_HOST')")
    public OfferPhotosDTO getById(@PathVariable Long id) {
        log.info("REST request to find internal offer by id: {}", id);
        return service.getById(id);
    }

}
