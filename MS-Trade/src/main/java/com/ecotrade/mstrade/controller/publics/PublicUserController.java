package com.ecotrade.mstrade.controller.publics;

import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.domain.dto.UserCreateDTO;
import com.ecotrade.mstrade.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Public User Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/user")
public class PublicUserController {
    private final UserService service;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseDTO create(@RequestPart("body") @Valid UserCreateDTO dto, @RequestPart(value = "file", required = false) MultipartFile photo) {
        log.info("Rest request to create user named: {}", dto.getName());
        return service.create(dto, photo);
    }
}
