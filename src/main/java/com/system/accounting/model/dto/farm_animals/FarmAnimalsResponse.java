package com.system.accounting.model.dto.farm_animals;

import com.system.accounting.model.entity.FarmAnimalEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FarmAnimalsResponse {

    private final List<FarmAnimalResponse> farmAnimals;

    public FarmAnimalsResponse(List<FarmAnimalEntity> entities) {
        this.farmAnimals = entities.stream()
                .map(FarmAnimalResponse::new)
                .collect(Collectors.toList());
    }
}
