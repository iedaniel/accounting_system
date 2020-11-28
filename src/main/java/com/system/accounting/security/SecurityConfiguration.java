package com.system.accounting.security;

import com.system.accounting.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] allowedUrls = {
            "/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    private final EmployeeRepository employeeRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(allowedUrls).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new ASAuthenticationFilter(employeeRepository));
    }
}
