package com.system.accounting.model.dto.bank_book.residents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.accounting.model.entity.PassportEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PassportDto {

    private String passportSeries;
    private String passportId;
    private String issuingAuthority;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate issueDate;

    public PassportDto(PassportEntity entity) {
        this.passportSeries = entity.getPassportSeries();
        this.passportId = entity.getPassportId();
        this.issuingAuthority = entity.getIssuingAuthority();
        this.issueDate = entity.getIssueDate();
    }
}
