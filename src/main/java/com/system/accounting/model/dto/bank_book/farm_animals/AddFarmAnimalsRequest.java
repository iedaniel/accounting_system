package com.system.accounting.model.dto.bank_book.farm_animals;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddFarmAnimalsRequest extends BankBookSpecifierRequest {

    private List<AddFarmAnimal> animals;
}
