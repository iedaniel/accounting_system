package com.system.accounting.model.dto.bank_book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.accounting.model.entity.BankBookEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BankBookResponse {

    private final String name;
    private final String creatorName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private final LocalDate closingDate;
    private final String closingReason;
    private final String address;
    private final String inn;
    private final String additionalInfo;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private final LocalDate creationDate;

    public BankBookResponse(BankBookEntity entity) {
        this.name = entity.getName();
        this.creatorName = entity.getCreator().getLogin();
        this.closingDate = entity.getClosingDate();
        this.closingReason = entity.getClosingReason();
        this.address = entity.getAddress();
        this.inn = entity.getInn();
        this.additionalInfo = entity.getAdditionalInfo();
        this.creationDate = entity.getCreationDate();
    }
}
