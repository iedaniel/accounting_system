package com.system.accounting.model.dto.farm_animals;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FarmAnimalCreateRequest {

    private String name;
    private String parentName;
}
