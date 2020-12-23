package com.system.accounting.model.dto.household_book;

import com.system.accounting.model.entity.HouseholdBookEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class HouseholdBooksResponse {

    private final List<HouseholdBookResponse> householdBooks;

    public HouseholdBooksResponse(List<HouseholdBookEntity> entities) {
        this.householdBooks = entities.stream()
                .map(HouseholdBookResponse::new)
                .collect(Collectors.toList());
    }
}
