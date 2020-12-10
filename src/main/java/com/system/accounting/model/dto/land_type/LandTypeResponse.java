package com.system.accounting.model.dto.land_type;

import com.system.accounting.model.entity.LandTypeEntity;
import lombok.Getter;

@Getter
public class LandTypeResponse {

    private final String name;
    private final String creatorName;

    public LandTypeResponse(LandTypeEntity entity) {
        this.name = entity.getName();
        this.creatorName = entity.getCreator().getLogin();
    }
}
