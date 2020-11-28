package com.system.accounting.service.registration;

import com.system.accounting.model.dto.BaseResponse;
import com.system.accounting.model.dto.employee.EmployeeCreateRequest;
import com.system.accounting.model.entity.EmployeeEntity;
import com.system.accounting.model.entity.EmployeeType;
import com.system.accounting.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegistrationService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void registration(EmployeeCreateRequest request) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setLogin(request.getLogin());
        employeeEntity.setPassword(request.getPassword());
        employeeEntity.setRole(EmployeeType.ORDINARY);
        employeeEntity.setFirstName(request.getFirstName());
        employeeEntity.setMiddleName(request.getMiddleName());
        employeeEntity.setLastName(request.getLastName());
        employeeRepository.save(employeeEntity);
    }
}
