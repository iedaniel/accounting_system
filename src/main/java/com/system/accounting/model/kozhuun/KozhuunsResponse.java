package com.system.accounting.model.kozhuun;

import com.system.accounting.model.entity.KozhuunEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class KozhuunsResponse {

    private final List<KozhuunResponse> kozhuuns;

    public KozhuunsResponse(List<KozhuunEntity> entities) {
        this.kozhuuns = entities.stream()
                .map(KozhuunResponse::new)
                .collect(Collectors.toList());
    }
}
