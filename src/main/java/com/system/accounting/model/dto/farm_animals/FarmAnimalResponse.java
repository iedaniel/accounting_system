package com.system.accounting.model.dto.farm_animals;

import com.system.accounting.model.entity.FarmAnimalEntity;
import lombok.Getter;

import java.util.Optional;

@Getter
public class FarmAnimalResponse {

    private final String name;
    private final String parentName;
    private final String creatorName;

    public FarmAnimalResponse(FarmAnimalEntity entity) {
        this.name = entity.getName();
        this.parentName = Optional.ofNullable(entity.getParent())
                .map(FarmAnimalEntity::getName)
                .orElse(null);
        this.creatorName = entity.getCreator().getLogin();
    }
}
