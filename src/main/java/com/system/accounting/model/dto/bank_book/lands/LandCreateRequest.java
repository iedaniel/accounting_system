package com.system.accounting.model.dto.bank_book.lands;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class LandCreateRequest extends BankBookSpecifierRequest {

    private String cadastralNumber;
    private String landCategory;
    private String totalArea;
    private String document;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate documentEndDate;
}
