package com.system.accounting.model.dto.bank_book;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.system.accounting.model.entity.BankBookEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BankBookResponse {

    private final String name;
    private final String creatorName;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate closingDate;
    private final String closingReason;

    public BankBookResponse(BankBookEntity entity) {
        this.name = entity.getName();
        this.creatorName = entity.getCreator().getLogin();
        this.closingDate = entity.getClosingDate();
        this.closingReason = entity.getClosingReason();
    }
}