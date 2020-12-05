package com.system.accounting.model.dto.bank_book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankBookCreateRequest {

    private String name;
    private String householdBookName;
    private String kozhuunName;
}
