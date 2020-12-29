package com.system.accounting.model.dto.bank_book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.HouseholdBookEntity;
import com.system.accounting.model.entity.ResidentEntity;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;
import java.util.Optional;

@Getter
public class BankBookResponse {

    private final String name;
    private final String mainFio;
    private final String village;
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
        this.mainFio = mainFio(entity);
        this.village = obtainVillage(entity);
        this.creatorName = entity.getCreator().getLogin();
        this.closingDate = entity.getClosingDate();
        this.closingReason = entity.getClosingReason();
        this.address = entity.getAddress();
        this.inn = entity.getInn();
        this.additionalInfo = entity.getAdditionalInfo();
        this.creationDate = entity.getCreationDate();
    }

    private String obtainVillage(BankBookEntity entity) {
        return Optional.ofNullable(entity)
                .map(BankBookEntity::getHouseholdBook)
                .map(HouseholdBookEntity::getVillageName)
                .orElse(null);
    }

    private String mainFio(BankBookEntity entity) {
        return entity.getResidents().stream()
                .filter(resident -> Strings.isBlank(resident.getRelation()))
                .findFirst()
                .map(ResidentEntity::getName)
                .orElse(null);
    }
}
