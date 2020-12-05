package com.system.accounting.model.dto.household_book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HouseholdBookCreateRequest {

    private String name;
    private String kozhuunName;
}
