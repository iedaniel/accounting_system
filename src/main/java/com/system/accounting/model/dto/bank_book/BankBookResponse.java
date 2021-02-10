package com.system.accounting.model.dto.bank_book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.accounting.service.repository.BankBookRepository.BankBookWithMainResident;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BankBookResponse {

    private final String name;
    private final String mainFio;
    private final String creatorName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private final LocalDate closingDate;
    private final String closingReason;
    private final String address;
    private final String inn;
    private final String additionalInfo;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private final LocalDate creationDate;

    public BankBookResponse(BankBookWithMainResident entity) {
        this.name = entity.getBankBook().getName();
        this.mainFio = entity.getMainFio();
        this.creatorName = entity.getBankBook().getCreator().getLogin();
        this.closingDate = entity.getBankBook().getClosingDate();
        this.closingReason = entity.getBankBook().getClosingReason();
        this.address = entity.getBankBook().getAddress();
        this.inn = entity.getBankBook().getInn();
        this.additionalInfo = entity.getBankBook().getAdditionalInfo();
        this.creationDate = entity.getBankBook().getCreationDate();
    }
}
