package com.system.accounting.model.dto.bank_book.farm_animals;

import com.system.accounting.model.entity.BankBookToFarmAnimalEntity;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class BookFarmAnimalsResponse {

    private final List<BookFarmAnimal> animals;

    public BookFarmAnimalsResponse(List<BankBookToFarmAnimalEntity> entities) {
        Set<BankBookToFarmAnimalEntity> uniqueEntities = new HashSet<>();
        entities.forEach(young -> {
            Optional<BankBookToFarmAnimalEntity> oldOpt = uniqueEntities.stream()
                    .filter(old -> old.getFarmAnimal().equals(young.getFarmAnimal()))
                    .findFirst();
            if (oldOpt.isEmpty()) {
                uniqueEntities.add(young);
            }
            if (oldOpt.isPresent() && oldOpt.get().getCreationDate().isBefore(young.getCreationDate())) {
                uniqueEntities.remove(oldOpt.get());
                uniqueEntities.add(young);
            }
        });
        this.animals = uniqueEntities.stream()
                .map(BookFarmAnimal::new)
                .collect(Collectors.toList());
    }
}
