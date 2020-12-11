package com.system.accounting.model.dto.agriculture;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgricultureCreateRequest {

    private String name;
    private String parentName;
}
