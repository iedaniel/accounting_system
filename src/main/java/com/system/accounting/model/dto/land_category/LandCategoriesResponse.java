package com.system.accounting.model.dto.land_category;

import com.system.accounting.model.entity.LandCategoryEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LandCategoriesResponse {

    private final List<LandCategoryResponse> categories;

    public LandCategoriesResponse(List<LandCategoryEntity> entities) {
        this.categories = entities.stream()
                .map(LandCategoryResponse::new)
                .collect(Collectors.toList());
    }
}
