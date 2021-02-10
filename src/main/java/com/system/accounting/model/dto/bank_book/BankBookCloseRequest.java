package com.system.accounting.model.dto.bank_book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.accounting.model.dto.bank_book.residents.ResidentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BankBookCloseRequest extends BankBookSpecifierRequest {

    private String closeReason;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate closeDate;
}
