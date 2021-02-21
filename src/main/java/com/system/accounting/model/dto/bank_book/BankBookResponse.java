package com.system.accounting.model.dto.bank_book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.HouseholdBookEntity;
import com.system.accounting.service.repository.BankBookRepository.BankBookWithMainResident;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BankBookResponse {

    private final String name;
    private final String householdBookName;
    private final String kozhuunName;
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
        BankBookEntity bankBook = entity.getBankBook();
        this.name = bankBook.getName();
        HouseholdBookEntity householdBook = bankBook.getHouseholdBook();
        this.householdBookName = householdBook.getName();
        this.kozhuunName = householdBook.getKozhuun().getName();
        this.mainFio = entity.getMainFio();
        this.creatorName = bankBook.getCreator().getLogin();
        this.closingDate = bankBook.getClosingDate();
        this.closingReason = bankBook.getClosingReason();
        this.address = bankBook.getAddress();
        this.inn = bankBook.getInn();
        this.additionalInfo = bankBook.getAdditionalInfo();
        this.creationDate = bankBook.getCreationDate();
    }
}
