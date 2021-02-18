package com.system.accounting.service.search;

import com.system.accounting.model.dto.bank_book.BankBooksResponse;
import com.system.accounting.service.repository.BankBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class SearchService {

    private final BankBookRepository bankBookRepository;

    @Transactional
    public BankBooksResponse findBankBooks(String query) {
        return new BankBooksResponse(bankBookRepository.findAllByQuery("%" + query.toLowerCase().trim() + "%"));
    }
}
