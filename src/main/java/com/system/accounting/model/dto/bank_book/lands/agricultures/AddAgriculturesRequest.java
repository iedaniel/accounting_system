package com.system.accounting.model.dto.bank_book.lands.agricultures;


import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddAgriculturesRequest extends BankBookSpecifierRequest {

    private String land;
    private List<AgricultureDto> agricultures;
}
