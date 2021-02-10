package com.system.accounting.model.dto.bank_book;

import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.service.repository.BankBookRepository;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BankBooksResponse {

    private final List<BankBookResponse> bankBooks;

    public BankBooksResponse(List<BankBookRepository.BankBookWithMainResident> entities) {
        this.bankBooks = entities.stream()
                .map(BankBookResponse::new)
                .collect(Collectors.toList());
    }
}
