package com.ecotrade.mstrade.controller;

import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.config.security.SecurityUtils;
import com.ecotrade.mstrade.domain.dto.OfferAdvertisementDTO;
import com.ecotrade.mstrade.domain.dto.OfferDTO;
import com.ecotrade.mstrade.domain.dto.OfferPhotosDTO;
import com.ecotrade.mstrade.domain.dto.OfferSimpleDTO;
import com.ecotrade.mstrade.service.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/offer")
@Tag(name = "Offer Controller")
public class OfferController {

    private final OfferService service;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Endpoint para criar ofertas para o usuário logado!", security = {@SecurityRequirement(name = "Auth")})
    public ResponseDTO create(@RequestPart("form") @Valid OfferSimpleDTO dto, @RequestPart("files") MultipartFile[] files) {
        log.info("REST request to create an offer with product: {}", dto.getProduct());
        return service.create(dto, files);
    }

    @PutMapping
    @PreAuthorize("hasPermission(#dto.id, 'OFFER')")
    @Operation(description = "Endpoint para editar ofertas do usuário logado!", security = {@SecurityRequirement(name = "Auth")})
    public ResponseDTO update(@RequestPart("form") @Valid OfferSimpleDTO dto, @RequestPart("files") MultipartFile[] files) {
        log.info("REST request to update an offer with product: {}", dto.getProduct());
        return service.update(dto, files);
    }

    @GetMapping("/advertisement/{id}")
    @PreAuthorize("hasPermission(#id, 'ADVERTISEMENT') or hasRole('ROLE_MASTER')")
    @Operation(description = "Endpoint para delete uma oferta do usuário logado!", security = {@SecurityRequirement(name = "Auth")})
    public List<OfferDTO> findAllByAdvertisementId(@PathVariable Long id) {
        log.info("REST request to get all offers of advertisement with id: {}", id);
        return service.getAllByAdvertisementId(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'OFFER')")
    @Operation(description = "Endpoint para delete uma oferta do usuário logado!", security = {@SecurityRequirement(name = "Auth")})
    public ResponseDTO deleteById(@PathVariable Long id) {
        log.info("REST request to delete an offer with id: {}", id);
        return service.deleteById(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'OFFER')")
    @Operation(description = "Endpoint para buscar uma oferta do usuário logado!", security = {@SecurityRequirement(name = "Auth")})
    public OfferPhotosDTO findById(@PathVariable Long id) {
        log.info("REST request to get a offer with id: {}", id);
        return service.getById(id);
    }

    @GetMapping("/my-offers")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Endpoint para buscar uma oferta do usuário logado!", security = {@SecurityRequirement(name = "Auth")})
    public List<OfferAdvertisementDTO> update() {
        log.info("REST request to get offers of user: {}", SecurityUtils.getAuthEmail());
        return service.getAllByUser();
    }
}
