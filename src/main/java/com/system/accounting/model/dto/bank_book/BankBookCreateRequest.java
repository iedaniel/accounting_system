package com.system.accounting.model.dto.bank_book;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BankBookCreateRequest {

    private String name;
    private String householdBookName;
    private String kozhuunName;
    private String address;
    private String inn;
    private String additionalInfo;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate creationDate;
}
