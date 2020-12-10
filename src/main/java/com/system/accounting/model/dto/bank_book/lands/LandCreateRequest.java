package com.system.accounting.model.dto.bank_book.lands;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LandCreateRequest extends BankBookSpecifierRequest {

    private String cadastralNumber;
    private String landCategory;
    private String totalArea;
    private String document;
    private String documentEndDate;
}
