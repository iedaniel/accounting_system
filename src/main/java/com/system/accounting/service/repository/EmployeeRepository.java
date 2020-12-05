package com.system.accounting.service.repository;

import com.system.accounting.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    EmployeeEntity findByLoginAndPassword(String login, String password);

    EmployeeEntity findByLogin(String login);
}
