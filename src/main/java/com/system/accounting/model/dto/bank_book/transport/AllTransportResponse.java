package com.system.accounting.model.dto.bank_book.transport;

import com.system.accounting.model.entity.TransportEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AllTransportResponse {

    private final List<TransportDto> transport;

    public AllTransportResponse(List<TransportEntity> entities) {
        this.transport = entities.stream()
                .map(TransportDto::new)
                .collect(Collectors.toList());
    }
}
