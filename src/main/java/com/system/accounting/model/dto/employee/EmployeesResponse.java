package com.system.accounting.model.dto.employee;


import com.system.accounting.model.entity.EmployeeEntity;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class EmployeesResponse {

    private final List<EmployeeResponse> employees;

    public EmployeesResponse(List<EmployeeEntity> entities) {
        this.employees = Optional.ofNullable(entities.stream())
                .orElse(Stream.empty())
                .map(EmployeeResponse::new)
                .collect(Collectors.toList());
    }
}
