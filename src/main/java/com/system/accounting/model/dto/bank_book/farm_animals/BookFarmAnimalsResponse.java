package com.system.accounting.model.dto.bank_book.farm_animals;

import com.system.accounting.model.entity.BankBookToFarmAnimalEntity;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
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
                .sorted(Comparator.comparing(Function.identity(), (e1, e2) -> {
                    String parentName1 = e1.getParentName() == null ? e1.getName() : e1.getParentName();
                    String parentName2 = e2.getParentName() == null ? e2.getName() : e2.getParentName();
                    return parentName1.compareTo(parentName2);
                }))
                .collect(Collectors.toList());
    }
}
