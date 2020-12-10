package com.system.accounting.model.dto.bank_book.lands;

import com.system.accounting.model.entity.LandEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LandsResponse {

    private final List<LandResponse> lands;

    public LandsResponse(List<LandEntity> entities) {
        this.lands = entities.stream()
                .map(LandResponse::new)
                .collect(Collectors.toList());
    }
}
