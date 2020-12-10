package com.system.accounting.model.dto.bank_book.residents;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddResidentsRequest extends BankBookSpecifierRequest {

    private List<ResidentDto> residents;
}
