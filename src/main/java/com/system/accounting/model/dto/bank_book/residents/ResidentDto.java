package com.system.accounting.model.dto.bank_book.residents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.accounting.model.entity.ResidentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ResidentDto {

    private Long id;
    private String name;
    private String relation;
    private String gender;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    private String residenceMark;
    private PassportDto passport;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate cancelDate;
    private String cancelReason;

    public ResidentDto(ResidentEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.relation = entity.getRelation();
        this.gender = entity.getGender();
        this.birthDate = entity.getBirthDate();
        this.residenceMark = entity.getResidenceMark();
        this.passport = Optional.ofNullable(entity.getPassport())
                .map(PassportDto::new)
                .orElse(null);
        this.cancelDate = entity.getCancelDate();
        this.cancelReason = entity.getCancelReason();
    }
}
