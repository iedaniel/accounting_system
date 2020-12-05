package com.system.accounting.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserInfoService {

    public String currentUserLogin() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }
}
