package com.system.accounting.model.dto.bank_book.lands.land_types;


import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddLandTypesRequest extends BankBookSpecifierRequest {

    private String land;
    List<LandTypeDto> landTypes;
}
