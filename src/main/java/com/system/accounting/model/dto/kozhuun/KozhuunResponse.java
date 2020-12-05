package com.system.accounting.model.dto.kozhuun;

import com.system.accounting.model.entity.KozhuunEntity;
import lombok.Getter;

@Getter
public class KozhuunResponse {

    private final String name;
    private final String creatorName;

    public KozhuunResponse(KozhuunEntity entity) {
        this.name = entity.getName();
        this.creatorName = entity.getCreator().getLogin();
    }
}
