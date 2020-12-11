package com.system.accounting.model.dto.bank_book.lands;

import com.system.accounting.model.entity.LandEntity;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class LandResponse {

    private final String cadastralNumber;
    private final String landCategory;
    private final String totalArea;
    private final String document;
    private final String documentEndDate;
    private final String creatorName;
    private final List<LandTypeAreaResponse> landTypes;
    private final List<LandAgricultureResponse> agricultures;

    public LandResponse(LandEntity entity) {
        this.cadastralNumber = entity.getCadastralNumber();
        this.landCategory = entity.getLandCategory();
        this.totalArea = entity.getTotalArea();
        this.document = entity.getDocument();
        this.documentEndDate = entity.getDocumentEndDate().toString();
        this.landTypes = Optional.ofNullable(entity.getLandTypes())
                .orElse(Collections.emptyList())
                .stream()
                .map(LandTypeAreaResponse::new)
                .collect(Collectors.toList());
        this.agricultures = Optional.ofNullable(entity.getAgricultures())
                .orElse(Collections.emptyList())
                .stream()
                .map(LandAgricultureResponse::new)
                .collect(Collectors.toList());
        this.creatorName = entity.getCreator().getLogin();
    }
}
