package com.ecotrade.mstrade.controller;

import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementFilterDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementSimpleDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementSingleOfferDTO;
import com.ecotrade.mstrade.domain.enuns.AdvertisementChangeStatus;
import com.ecotrade.mstrade.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/advertisement")
@Tag(name = "Advertisement Controller")
public class AdvertisementController {
    private final AdvertisementService service;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Endpoint para criar anúncios para o usuário logado!", security = {@SecurityRequirement(name = "Auth")})
    public ResponseDTO create(@RequestPart("form") @Valid AdvertisementDTO dto, @RequestPart("files") MultipartFile[] files) {
        log.info("REST request to create an advertisement with title: {}", dto.getTitle());
        return service.create(dto, files);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MASTER') OR hasPermission(#id, 'ADVERTISEMENT')")
    @Operation(description = "Endpoint para buscar os anúncios presentes na plataforma", security = {@SecurityRequirement(name = "Auth")})
    public ResponseDTO delete(@PathVariable Long id) {
        log.info("REST request to delete advertisement with id: {}", id);
        return service.delete(id);
    }

    @PostMapping("/filter")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Endpoint para buscar os anúncios presentes na plataforma", security = {@SecurityRequirement(name = "Auth")})
    public Page<AdvertisementSimpleDTO> filter(Pageable pageable, AdvertisementFilterDTO filter) {
        log.info("REST request to filter advertisement");
        return service.filter(pageable, filter);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MASTER') OR hasPermission(#id, 'ADVERTISEMENT')")
    @Operation(description = "Endpoint para buscar o anúncio presente na plataforma", security = {@SecurityRequirement(name = "Auth")})
    public AdvertisementSimpleDTO getById(@PathVariable Long id) {
        log.info("REST request to get by id advertisement with id: {}", id);
        return service.getById(id);
    }

    @GetMapping("/my-advertisement")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Endpoint para buscar o anúncio presente na plataforma", security = {@SecurityRequirement(name = "Auth")})
    public List<AdvertisementSimpleDTO> findMyAdvertisement() {
        log.info("REST request to find my advertisements");
        return service.findMyAdvertisement();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_MASTER') OR hasPermission(#dto.id, 'ADVERTISEMENT')")
    @Operation(description = "Endpoint para atualizar o anúncio presente na plataforma", security = {@SecurityRequirement(name = "Auth")})
    public ResponseDTO update(@RequestPart("form") @Valid AdvertisementDTO dto, @RequestPart(value = "files", required = false) MultipartFile[] files) {
        log.info("REST request to update advertisement with id: {}", dto.getId());
        return service.update(dto, files);
    }

    @PostMapping("/{id}/status/offer/{offerId}")
    @PreAuthorize("hasAuthority('ROLE_MASTER') OR hasPermission(#id, 'ADVERTISEMENT')")
    @Operation(description = "Endpoint para aceitar uma oferta feita para um anúncio presente na plataforma", security = {@SecurityRequirement(name = "Auth")})
    public ResponseDTO changeStatusOfOffer(@PathVariable Long id, @PathVariable Long offerId, @RequestParam("status") AdvertisementChangeStatus status) {
        log.info("REST request to accept offer: {} for advertisement with id: {}, with status: {}", offerId, id, status);
        return service.changeStatusOfOffer(id, offerId, status);
    }

    @GetMapping("/{id}/offer/{offerId}")
    @PreAuthorize("hasPermission(#id, 'ADVERTISEMENT') or hasPermission(#offerId, 'OFFER') or hasRole('ROLE_MASTER')")
    @Operation(description = "<h2>Endpoint para visualizar uma determinada oferta de um anúncio!</h2> \n<h4>Permissões:<h4> <ul>\n<li>Ofertante</li><li>Anúnciante</li><li>Master</li></ul>", security = {@SecurityRequirement(name = "Auth")})
    public AdvertisementSingleOfferDTO offerAnalises(@PathVariable Long id, @PathVariable Long offerId) {
        log.info("REST request to visualize advetisement: {} an the offer: {}", id, offerId);
        return service.getOfferAnalises(id, offerId);
    }
}
