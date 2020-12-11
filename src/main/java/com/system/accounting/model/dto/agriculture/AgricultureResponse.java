package com.system.accounting.model.dto.agriculture;

import com.system.accounting.model.entity.AgricultureEntity;
import lombok.Getter;

import java.util.Optional;

@Getter
public class AgricultureResponse {

    private final String name;
    private final String parentName;
    private final String creatorName;

    public AgricultureResponse(AgricultureEntity entity) {
        this.name = entity.getName();
        this.parentName = Optional.ofNullable(entity.getParent())
                .map(AgricultureEntity::getName)
                .orElse(null);
        this.creatorName = entity.getCreator().getLogin();
    }
}
