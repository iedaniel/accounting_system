package com.system.accounting.model.dto.agriculture;

import com.system.accounting.model.entity.AgricultureEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AgriculturesResponse {

    private final List<AgricultureResponse> agricultures;

    public AgriculturesResponse(List<AgricultureEntity> entities) {
        this.agricultures = entities.stream()
                .map(AgricultureResponse::new)
                .collect(Collectors.toList());
    }
}
