package com.system.accounting.model.dto.bank_book.lands;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LandSpecifierRequest extends BankBookSpecifierRequest {

    private String cadastralNumber;
}
