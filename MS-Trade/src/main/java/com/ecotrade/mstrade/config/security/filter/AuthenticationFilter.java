package com.ecotrade.mstrade.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecotrade.mstrade.config.PropertiesConfig;
import com.ecotrade.mstrade.domain.dto.UserDetailsDTO;
import com.ecotrade.mstrade.service.NoSqlService;
import com.ecotrade.mstrade.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final PropertiesConfig config;
    private final NoSqlService noSqlService;
    private final UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsDTO user = (UserDetailsDTO) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(config.getSecret().getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + config.getTime()))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        setInRedis(accessToken, user);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        log.info("Successful login to : {}", user.getUsername());
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private void setInRedis(String accessToken, UserDetailsDTO user) throws JsonProcessingException {
        log.info("Setting token: {} in REDIS", accessToken);
        Map<String, Object> obj = new HashMap<>();
        obj.put("id", user.getId());
        obj.put("email", user.getUsername());
        obj.put("roles", user.getAuthorities().stream().map(it -> it.getAuthority()).collect(Collectors.toList()));
        noSqlService.setValue(accessToken, new ObjectMapper().writeValueAsString(obj), TimeUnit.DAYS, 7L);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(204);
    }
}
