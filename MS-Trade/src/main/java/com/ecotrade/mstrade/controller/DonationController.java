package com.ecotrade.mstrade.controller;

import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.service.AdvertisementService;
import com.ecotrade.mstrade.service.DonationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/donation")
@Tag(name = "Donation Controller")
public class DonationController {

    private final DonationService service;

    @PostMapping("/organization/{organizationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseDTO confirmRecivement(@PathVariable Long organizationId, @RequestParam Integer quantity) {
        log.info("REST request to donation: {} to organization: {}", quantity, organizationId);
        return service.donate(quantity, organizationId);
    }

}
