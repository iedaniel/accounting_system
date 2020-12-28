package com.system.accounting.model.dto;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;

@Getter
public class UserProfile {

    private final String username;
    private final String fio;
    private final String role;

    public UserProfile(DecodedJWT decodedJWT) {
        this.username = decodedJWT.getSubject();
        this.fio = decodedJWT.getClaim("fio").asString();
        this.role = decodedJWT.getClaim("role").asString();
    }
}
