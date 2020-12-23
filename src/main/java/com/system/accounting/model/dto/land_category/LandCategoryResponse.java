package com.system.accounting.model.dto.land_category;

import com.system.accounting.model.entity.LandCategoryEntity;
import lombok.Getter;

@Getter
public class LandCategoryResponse {

    private final String name;
    private final String creatorName;

    public LandCategoryResponse(LandCategoryEntity entity) {
        this.name = entity.getName();
        this.creatorName = entity.getCreator().getLogin();
    }
}
