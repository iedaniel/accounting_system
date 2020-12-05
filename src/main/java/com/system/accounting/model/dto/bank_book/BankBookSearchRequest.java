package com.system.accounting.model.dto.bank_book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankBookSearchRequest {

    private String kozhuunName;
    private String householdBookName;
}
