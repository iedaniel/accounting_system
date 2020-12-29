package com.system.accounting.model.dto.bank_book.transport;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransportCreateRequest extends BankBookSpecifierRequest {

    private String name;
    private Integer year;
    private Integer num;
    private String rights;
}
