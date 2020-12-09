package com.system.accounting.service.farm_animals;

import com.system.accounting.model.dto.farm_animals.FarmAnimalCreateRequest;
import com.system.accounting.model.dto.farm_animals.FarmAnimalsResponse;
import com.system.accounting.model.entity.FarmAnimalEntity;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.EmployeeRepository;
import com.system.accounting.service.repository.FarmAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FarmAnimalService {

    private final FarmAnimalRepository farmAnimalRepository;
    private final EmployeeRepository employeeRepository;
    private final UserInfoService userInfoService;

    @Transactional
    public void create(FarmAnimalCreateRequest request) {
        FarmAnimalEntity entity = new FarmAnimalEntity();
        entity.setName(request.getName());
        Optional.ofNullable(request.getParentName())
                .map(farmAnimalRepository::findByName)
                .ifPresent(entity::setParent);
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        farmAnimalRepository.save(entity);
    }

    @Transactional
    public FarmAnimalsResponse getFarmAnimals() {
        return new FarmAnimalsResponse(farmAnimalRepository.findAll());
    }
}
