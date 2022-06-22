package com.ecotrade.mstrade.controller;

import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.service.AdvertisementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/receivement")
@Tag(name = "Recivement Controller")
public class ReceivementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/advertisement/{id}/offer/{offerId}")
    @PreAuthorize("hasAuthority('ROLE_MASTER') OR hasPermission(#id, 'ADVERTISEMENT')")
    public ResponseDTO confirmRecivement(@PathVariable Long id, @PathVariable Long offerId) {
        log.info("REST request to confirm the trade");
        return advertisementService.confirmRecive(id, offerId);
    }

}
