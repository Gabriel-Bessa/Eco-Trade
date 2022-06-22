package com.ecotrade.mstrade.controller.publics;

import com.ecotrade.mstrade.domain.dto.AdvertisementFilterDTO;
import com.ecotrade.mstrade.domain.dto.AdvertisementSimpleDTO;
import com.ecotrade.mstrade.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/advertisement")
@Tag(name = "Public Advertisement Controller")
public class PublicAdvertisementController {
    private final AdvertisementService service;

    @PostMapping("/filter")
    @PreAuthorize("permitAll()")
    public Page<AdvertisementSimpleDTO> filter(Pageable pageable, AdvertisementFilterDTO filter) {
        log.info("REST request to filter advertisement");
        return service.filter(pageable, filter);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public AdvertisementSimpleDTO getById(@PathVariable Long id) {
        log.info("REST request to get by id advertisement with id: {}", id);
        return service.getById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    public List<AdvertisementSimpleDTO> searchAdvertisement(@RequestParam String title) {
        log.info("REST request to search by title: {}", title);
        return service.findAllBySearch(title);
    }
}
