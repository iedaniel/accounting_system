package com.system.accounting.model.dto.bank_book.residents;

import com.system.accounting.model.entity.ResidentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ResidentDto {

    private String name;
    private String relation;
    private String gender;
    private String birthDate;
    private String residenceMark;
    private PassportDto passport;

    public ResidentDto(ResidentEntity entity) {
        this.name = entity.getName();
        this.relation = entity.getRelation();
        this.gender = entity.getGender();
        this.birthDate = entity.getBirthDate().toString();
        this.residenceMark = entity.getResidenceMark();
        this.passport = Optional.ofNullable(entity.getPassport())
                .map(PassportDto::new)
                .orElse(null);;
    }
}
