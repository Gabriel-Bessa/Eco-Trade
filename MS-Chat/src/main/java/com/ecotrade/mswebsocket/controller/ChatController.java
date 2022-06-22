package com.ecotrade.mswebsocket.controller;

import com.ecotrade.mswebsocket.domain.dto.MessageDTO;
import com.ecotrade.mswebsocket.domain.dto.MessageSimpleDTO;
import com.ecotrade.mswebsocket.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController("/chat")
public class ChatController {

    private final ChatService chatService;


    @PostMapping("/advertisement/{advertisementId}/offer/{offerId}")
    @PreAuthorize("hasAuthority('ROLE_MASTER') OR hasPermission(#advertisementId, 'ADVERTISEMENT') OR hasPermission(#offerId, 'OFFER')")
    @Operation(description = "<h2>Endpoint para enviar um menssagem ao outro lado do anúncio, se o anunciante manda a request, a messagem é encaminhada ao ofertante e vise versa!</h2> \n<h4>Permissões:<h4> <ul>\n<li>Ofertante</li><li>Anúnciante</li></ul>", security = {@SecurityRequirement(name = "Auth")})
    public void sendMessage(@PathVariable Long advertisementId, @PathVariable Long offerId, @RequestBody @Valid MessageDTO message) {
        log.info("REST request to send message of advertisement: {} to offer: {}", advertisementId, offerId);
        chatService.sendMenssage(advertisementId, offerId, message);
    }

    @GetMapping("/advertisement/{advertisementId}/offer/{offerId}")
    @PreAuthorize("hasAuthority('ROLE_MASTER') OR hasPermission(#advertisementId, 'ADVERTISEMENT') OR hasPermission(#offerId, 'OFFER')")
    @Operation(description = "<h2>Endpoint para enviar um menssagem ao outro lado do anúncio, se o anunciante manda a request, a messagem é encaminhada ao ofertante e vise versa!</h2> \n<h4>Permissões:<h4> <ul>\n<li>Ofertante</li><li>Anúnciante</li></ul>", security = {@SecurityRequirement(name = "Auth")})
    public List<MessageSimpleDTO> getAllMessages(@PathVariable Long advertisementId, @PathVariable Long offerId) {
        log.info("REST request to get all for advertisement: {} and offer: {}", advertisementId, offerId);
        return chatService.getAllMessage(advertisementId, offerId);
    }
}
