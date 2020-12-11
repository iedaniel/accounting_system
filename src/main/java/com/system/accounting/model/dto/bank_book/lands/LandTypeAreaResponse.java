package com.system.accounting.model.dto.bank_book.lands;

import com.system.accounting.model.entity.BankBookToLandTypeEntity;
import lombok.Getter;

@Getter
public class LandTypeAreaResponse {

    private final String landType;
    private final String area;
    private final String creatorName;

    public LandTypeAreaResponse(BankBookToLandTypeEntity entity) {
        this.landType = entity.getLandType().getName();
        this.area = entity.getValue();
        this.creatorName = entity.getCreator().getLogin();
    }
}
