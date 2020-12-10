package com.system.accounting.model.dto.land_type;

import com.system.accounting.model.entity.LandTypeEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LandTypesResponse {

    private final List<LandTypeResponse> landTypes;

    public LandTypesResponse(List<LandTypeEntity> entities) {
        this.landTypes = entities.stream()
                .map(LandTypeResponse::new)
                .collect(Collectors.toList());
    }
}
