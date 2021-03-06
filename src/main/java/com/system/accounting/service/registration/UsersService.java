package com.system.accounting.service.registration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.system.accounting.model.dto.TokenResponse;
import com.system.accounting.model.dto.UserLogin;
import com.system.accounting.model.dto.UserProfile;
import com.system.accounting.model.dto.employee.EmployeeCreateRequest;
import com.system.accounting.model.dto.employee.EmployeesResponse;
import com.system.accounting.model.entity.EmployeeEntity;
import com.system.accounting.model.entity.EmployeeType;
import com.system.accounting.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void registration(EmployeeCreateRequest request) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setLogin(request.getLogin());
        employeeEntity.setPassword(request.getPassword());
        employeeEntity.setRole(request.getRole());
        employeeEntity.setFirstName(request.getFirstName());
        employeeEntity.setMiddleName(request.getMiddleName());
        employeeEntity.setLastName(request.getLastName());
        employeeRepository.save(employeeEntity);
    }

    @Transactional
    public EmployeesResponse getEmployees() {
        List<EmployeeEntity> entities = employeeRepository.findAll();
        return new EmployeesResponse(entities);
    }

    @Transactional
    public TokenResponse login(UserLogin user) {
        EmployeeEntity employeeEntity = employeeRepository.findByLoginAndPassword(
                user.getUsername(),
                user.getPassword()
        );
        if (employeeEntity == null) {
            throw new RuntimeException("Cannot find user with such login and password");
        }
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(12 * 60 * 60)))
                .withClaim("role", employeeEntity.getRole().name())
                .withClaim("fio", employeeEntity.getFio())
                .sign(Algorithm.HMAC512("test"));
        return new TokenResponse(token);
    }

    public UserProfile profile(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return new UserProfile(decodedJWT);
    }
}
