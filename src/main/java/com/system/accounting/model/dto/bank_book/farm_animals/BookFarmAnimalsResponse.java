package com.system.accounting.model.dto.bank_book.farm_animals;

import com.system.accounting.model.entity.BankBookToFarmAnimalEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BookFarmAnimalsResponse {

    private final List<BookFarmAnimal> animals;

    public BookFarmAnimalsResponse(List<BankBookToFarmAnimalEntity> entities) {
        this.animals = entities.stream()
                .map(BookFarmAnimal::new)
                .collect(Collectors.toList());
    }
}
