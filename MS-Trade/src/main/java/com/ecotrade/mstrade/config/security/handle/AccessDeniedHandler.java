package com.ecotrade.mstrade.config.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import software.amazon.ion.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("Access Denied to token: {}", req.getHeader("Authorization"));
        ObjectMapper mapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        res.getWriter().write(mapper.writeValueAsString(getDefaultResponse()));
    }

    private Object getDefaultResponse() {
        Map<String, String> resp = new LinkedHashMap<>();
        resp.put("message", "Acesso negado");
        resp.put("cause", "Você não tem permissão para acessar este recurso");
        resp.put("time", String.valueOf(Timestamp.now()));
        return resp;
    }
}
