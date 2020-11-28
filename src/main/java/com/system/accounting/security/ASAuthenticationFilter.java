package com.system.accounting.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.accounting.model.dto.UserLogin;
import com.system.accounting.model.entity.EmployeeEntity;
import com.system.accounting.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        response.addHeader("Yeah", "bitch!!!");
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
