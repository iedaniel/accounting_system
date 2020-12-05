package com.system.accounting.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.util.HttpResponseWriter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

@Slf4j
public class ASAuthorizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = processToken(request.getHeader("Authorization"));
        if (token == null) {
            new HttpResponseWriter(response).write(new BaseResponse<>("Unauthorized access"));
            chain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedToken = JWT.decode(token);
        boolean isExpired = decodedToken.getExpiresAt().before(Date.from(Instant.now()));
        if (isExpired) {
            new HttpResponseWriter(response).write(new BaseResponse<>("Token has expired"));
            chain.doFilter(request, response);
            return;
        }

        log.info("\n\nПолучили запрос от " + decodedToken.getSubject());

        chain.doFilter(request, response);
    }

    private String processToken(String token) {
        return Optional.ofNullable(token)
                .map(t -> t.replace("Bearer ", ""))
                .orElse(null);
    }
}
