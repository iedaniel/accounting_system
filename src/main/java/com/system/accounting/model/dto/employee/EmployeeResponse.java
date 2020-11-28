package com.system.accounting.model.dto.employee;

import com.system.accounting.model.entity.EmployeeEntity;
import lombok.Getter;

@Getter
public class EmployeeResponse {

    private final String login;
    private final String role;
    private final String fio;

    public EmployeeResponse(EmployeeEntity entity) {
        this.login = entity.getLogin();
        this.role = entity.getRole().name();
        this.fio = String.format("%s %s %s", entity.getFirstName(), entity.getMiddleName(), entity.getLastName());
    }
}
