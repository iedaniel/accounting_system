package com.system.accounting.model.dto.household_book;

import com.system.accounting.model.entity.HouseholdBookEntity;
import lombok.Getter;

@Getter
public class HouseholdBookResponse {

    private final String name;
    private final String kozhuunName;
    private final String creatorName;

    public HouseholdBookResponse(HouseholdBookEntity entity) {
        this.name = entity.getName();
        this.kozhuunName = entity.getKozhuun().getName();
        this.creatorName = entity.getCreator().getLogin();
    }
}
