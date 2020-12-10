package com.system.accounting.model.dto.bank_book.residents;

import com.system.accounting.model.entity.ResidentEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BookResidentsResponse {

    private final List<ResidentDto> residents;

    public BookResidentsResponse(List<ResidentEntity> entities) {
        this.residents = entities.stream()
                .map(ResidentDto::new)
                .collect(Collectors.toList());
    }
}
