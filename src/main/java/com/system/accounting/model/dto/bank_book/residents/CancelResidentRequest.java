package com.system.accounting.model.dto.bank_book.residents;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CancelResidentRequest {

    private Long id;
    private String cancelReason;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate cancelDate;
}
