package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeType {
    ADMIN("ADMIN"),
    ORDINARY("ORDINARY");

    private final String role;
}
