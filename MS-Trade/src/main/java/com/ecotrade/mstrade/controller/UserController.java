package com.ecotrade.mstrade.controller;

import com.ecotrade.mstrade.config.responses.ResponseDTO;
import com.ecotrade.mstrade.config.security.SecurityUtils;
import com.ecotrade.mstrade.domain.dto.UserCreateDTO;
import com.ecotrade.mstrade.domain.dto.UserDTO;
import com.ecotrade.mstrade.domain.dto.UserDetailsDTO;
import com.ecotrade.mstrade.domain.dto.UserFullNoPasswordDTO;
import com.ecotrade.mstrade.domain.dto.UserPasswordDTO;
import com.ecotrade.mstrade.domain.dto.UserUpdateDTO;
import com.ecotrade.mstrade.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller")
public class UserController {
    private final UserService service;

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Endpoint para alterar a senha do usuário logado", security = {@SecurityRequirement(name = "Auth")})
    public ResponseDTO changePassword(@RequestBody @Valid UserPasswordDTO passwordDTO) {
        log.info("Rest request to change password of user: {}", SecurityUtils.getAuthEmail());
        return service.changePassword(passwordDTO);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Endpoint para buscar os dados do usuário logado", security = {@SecurityRequirement(name = "Auth")})
    public UserFullNoPasswordDTO me() {
        log.info("Rest request to get my infos user: {}", SecurityUtils.getAuthEmail());
        return service.me();
    }

    @Hidden
    @GetMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ROLE_HOST_TO_HOST')")
    public UserDetailsDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseDTO update(@RequestBody @Valid UserUpdateDTO user) {
        log.info("REST request to update user with id: {}", user.getId());
        return service.update(user);
    }
}

