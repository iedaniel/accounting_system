package com.system.accounting.model.dto.bank_book.residents;

import com.system.accounting.model.entity.PassportEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PassportDto {

    private String passportSeries;
    private String passportId;
    private String issuingAuthority;
    private String issueDate;

    public PassportDto(PassportEntity entity) {
        this.passportSeries = entity.getPassportSeries();
        this.passportId = entity.getPassportId();
        this.issuingAuthority = entity.getIssuingAuthority();
        this.issueDate = entity.getIssueDate().toString();
    }
}
