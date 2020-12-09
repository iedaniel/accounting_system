package com.system.accounting.model.dto.bank_book.farm_animals;

import com.system.accounting.model.entity.BankBookToFarmAnimalEntity;
import com.system.accounting.model.entity.FarmAnimalEntity;
import lombok.Getter;

import java.util.Optional;

@Getter
public class BookFarmAnimal {

    private final String name;
    private final String parentName;
    private final String value;

    public BookFarmAnimal(BankBookToFarmAnimalEntity entity) {
        this.name = entity.getFarmAnimal().getName();
        this.parentName = Optional.ofNullable(entity.getFarmAnimal().getParent())
                .map(FarmAnimalEntity::getName)
                .orElse(null);
        this.value = entity.getValue();
    }
}
