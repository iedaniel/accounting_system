package com.system.accounting.model.dto.employee;

import com.system.accounting.model.entity.EmployeeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeCreateRequest {

    private String login;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private EmployeeType role;
}
