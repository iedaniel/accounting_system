package com.system.accounting.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.accounting.model.dto.UserLogin;
import com.system.accounting.model.entity.EmployeeEntity;
import com.system.accounting.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;

@RequiredArgsConstructor
public class ASAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final EmployeeRepository employeeRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLogin user = retrieveUser(request);
        EmployeeEntity employeeEntity = employeeRepository.findByLoginAndPassword(
                user.getUsername(),
                user.getPassword()
        );
        if (employeeEntity == null) {
            throw new RuntimeException("Введены неверные данные");
        }

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(12 * 60 * 60)))
                .withClaim("role", employeeEntity.getRole().name())
                .sign(Algorithm.HMAC512("test"));
        response.addHeader("Token", token);
        return null;
    }

    private UserLogin retrieveUser(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), UserLogin.class);
        } catch (IOException e) {
            throw new RuntimeException("Не получилось извлечь данные пользователя из запроса");
        }
    }
}
