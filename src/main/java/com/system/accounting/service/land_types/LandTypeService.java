package com.system.accounting.service.land_types;

import com.system.accounting.model.dto.land_type.LandTypeRequest;
import com.system.accounting.model.dto.land_type.LandTypesResponse;
import com.system.accounting.model.entity.LandTypeEntity;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.EmployeeRepository;
import com.system.accounting.service.repository.LandTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LandTypeService {

    private final UserInfoService userInfoService;
    private final EmployeeRepository employeeRepository;
    private final LandTypeRepository landTypeRepository;

    @Transactional
    public void createLandType(LandTypeRequest request) {
        LandTypeEntity entity = new LandTypeEntity();
        entity.setName(request.getName());
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        landTypeRepository.save(entity);
    }

    @Transactional
    public LandTypesResponse getLandTypes() {
        return new LandTypesResponse(landTypeRepository.findAll());
    }
}
