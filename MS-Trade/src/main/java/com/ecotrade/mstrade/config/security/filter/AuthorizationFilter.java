package com.ecotrade.mstrade.config.security.filter;

import com.ecotrade.mstrade.domain.dto.UserDetailsDTO;
import com.ecotrade.mstrade.service.NoSqlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final NoSqlService noSqlService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            Object value = noSqlService.getValue(token);
            if (value == null) {
                returnInvalidToken(response);
                return;
            }
            doAuthorization(value);
        }
        filterChain.doFilter(request, response);
    }

    private void returnInvalidToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, String> error = new HashMap<>();
        error.put("property", "Falha na autenticação");
        error.put("message", "O token informado é inválido ou expirou");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    private void doAuthorization(Object value) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetailsDTO dto = desearializeObj(value);
                SecurityContextHolder.getContext().setAuthentication(getAuthenticationFromRedisMap(dto));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private UserDetailsDTO desearializeObj(Object objFromRedis) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue((String) objFromRedis, Map.class);
        List<SimpleGrantedAuthority> roles = ((List<String>) map.get("roles")).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UserDetailsDTO(((Integer) map.get("id")).longValue(), (String) map.get("email"), null, roles);
    }

    private Authentication getAuthenticationFromRedisMap(UserDetailsDTO dto) {
        return new UsernamePasswordAuthenticationToken(dto, null, dto.getAuthorities());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class UserRolesDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String email;
        private Long id;
        private List<SimpleGrantedAuthority> roles;
    }
}

