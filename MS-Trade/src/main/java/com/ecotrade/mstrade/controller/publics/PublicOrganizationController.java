package com.ecotrade.mstrade.controller.publics;

import com.ecotrade.mstrade.domain.dto.OrganizationDTO;
import com.ecotrade.mstrade.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/organization")
@Tag(name = "Public Organization Controller")
public class PublicOrganizationController {

    private final OrganizationService service;

    @GetMapping
    @PreAuthorize("permitAll()")
    @Operation(description = "<h2>Endpoint para visualizar as organizações para as quais se pode doar!</h2> \n<h4>Permissões:<h4> <ul>\n<li>Publica</li></ul>")
    public List<OrganizationDTO> findAllOrganizations() {
        log.info("REST request to find all organizations");
        return service.findAllOrganization();
    }

}
