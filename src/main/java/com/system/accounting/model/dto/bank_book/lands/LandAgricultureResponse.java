package com.system.accounting.model.dto.bank_book.lands;

import com.system.accounting.model.entity.AgricultureEntity;
import com.system.accounting.model.entity.LandToAgricultureEntity;
import lombok.Getter;

import java.util.Optional;

@Getter
public class LandAgricultureResponse {

    private final String agriculture;
    private final String parentAgriculture;
    private final String area;
    private final String creatorName;

    public LandAgricultureResponse(LandToAgricultureEntity entity) {
        this.agriculture = entity.getAgriculture().getName();
        this.parentAgriculture = Optional.ofNullable(entity.getAgriculture().getParent())
                .map(AgricultureEntity::getName)
                .orElse(null);
        this.area = entity.getArea();
        this.creatorName = entity.getCreator().getLogin();
    }
}
