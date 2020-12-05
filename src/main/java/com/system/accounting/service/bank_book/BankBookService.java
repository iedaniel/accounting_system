package com.system.accounting.service.bank_book;

import com.system.accounting.model.dto.bank_book.BankBookCreateRequest;
import com.system.accounting.model.dto.bank_book.BankBookSearchRequest;
import com.system.accounting.model.dto.bank_book.BankBooksResponse;
import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.HouseholdBookEntity;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.BankBookRepository;
import com.system.accounting.service.repository.EmployeeRepository;
import com.system.accounting.service.repository.HouseholdBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BankBookService {

    private final HouseholdBookRepository householdBookRepository;
    private final EmployeeRepository employeeRepository;
    private final BankBookRepository bankBookRepository;
    private final UserInfoService userInfoService;

    @Transactional
    public void createBankBook(BankBookCreateRequest request) {
        HouseholdBookEntity householdBook = householdBookRepository.findByNameAndKozhuunName(
                request.getHouseholdBookName(),
                request.getKozhuunName()
        );
        BankBookEntity entity = new BankBookEntity();
        entity.setName(request.getName());
        entity.setHouseholdBook(householdBook);
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        bankBookRepository.save(entity);
    }

    @Transactional
    public BankBooksResponse getBankBooks(BankBookSearchRequest request) {
        return new BankBooksResponse(bankBookRepository.findAllByHouseholdBookNameAndHouseholdBookKozhuunName(
                request.getHouseholdBookName(),
                request.getKozhuunName()
        ));
    }
}
