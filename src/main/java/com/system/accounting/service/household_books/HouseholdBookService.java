package com.system.accounting.service.household_books;

import com.system.accounting.model.dto.household_book.HouseholdBookCreateRequest;
import com.system.accounting.model.dto.household_book.HouseholdBooksResponse;
import com.system.accounting.model.entity.HouseholdBookEntity;
import com.system.accounting.model.entity.KozhuunEntity;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.EmployeeRepository;
import com.system.accounting.service.repository.HouseholdBookRepository;
import com.system.accounting.service.repository.KozhuunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HouseholdBookService {

    private final HouseholdBookRepository householdBookRepository;
    private final KozhuunRepository kozhuunRepository;
    private final EmployeeRepository employeeRepository;
    private final UserInfoService userInfoService;

    @Transactional
    public void createHouseholdBook(HouseholdBookCreateRequest request) {
        KozhuunEntity kozhuun = kozhuunRepository.findByName(request.getKozhuunName());
        if (kozhuun == null) {
            throw new RuntimeException("Кожуун не найден");
        }
        HouseholdBookEntity entity = new HouseholdBookEntity();
        entity.setName(request.getName());
        entity.setVillageName(request.getVillageName());
        entity.setKozhuun(kozhuun);
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        householdBookRepository.save(entity);
    }

    @Transactional
    public HouseholdBooksResponse getHouseholdBooks() {
        return new HouseholdBooksResponse(householdBookRepository.findAll());
    }
}
