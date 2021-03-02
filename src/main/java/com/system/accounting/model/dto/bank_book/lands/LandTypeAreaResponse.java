package com.system.accounting.model.dto.bank_book.lands;

import com.system.accounting.model.entity.LandToLandTypeEntity;
import lombok.Getter;

@Getter
public class LandTypeAreaResponse {

    private final String landType;
    private final Double area;
    private final String creatorName;

    public LandTypeAreaResponse(LandToLandTypeEntity entity) {
        this.landType = entity.getLandType().getName();
        this.area = entity.getValue();
        this.creatorName = entity.getCreator().getLogin();
    }
}
