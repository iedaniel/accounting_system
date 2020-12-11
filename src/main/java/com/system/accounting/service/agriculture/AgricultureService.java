package com.system.accounting.service.agriculture;

import com.system.accounting.model.dto.agriculture.AgricultureCreateRequest;
import com.system.accounting.model.dto.agriculture.AgriculturesResponse;
import com.system.accounting.model.entity.AgricultureEntity;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.AgricultureRepository;
import com.system.accounting.service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AgricultureService {

    private final AgricultureRepository agricultureRepository;
    private final UserInfoService userInfoService;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void create(AgricultureCreateRequest request) {
        AgricultureEntity entity = new AgricultureEntity();
        entity.setName(request.getName());
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        if (request.getParentName() != null) {
            entity.setParent(agricultureRepository.findByName(request.getParentName()));
        }
        agricultureRepository.save(entity);
    }

    @Transactional
    public AgriculturesResponse getCultures() {
        return new AgriculturesResponse(agricultureRepository.findAll());
    }
}
