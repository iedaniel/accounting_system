package com.system.accounting.service.bank_book;

import com.system.accounting.model.dto.bank_book.BankBookCreateRequest;
import com.system.accounting.model.dto.bank_book.BankBookSearchRequest;
import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import com.system.accounting.model.dto.bank_book.BankBooksResponse;
import com.system.accounting.model.dto.bank_book.farm_animals.AddFarmAnimalsRequest;
import com.system.accounting.model.dto.bank_book.farm_animals.BookFarmAnimalsResponse;
import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.BankBookToFarmAnimalEntity;
import com.system.accounting.model.entity.FarmAnimalEntity;
import com.system.accounting.model.entity.HouseholdBookEntity;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.BankBookRepository;
import com.system.accounting.service.repository.EmployeeRepository;
import com.system.accounting.service.repository.FarmAnimalRepository;
import com.system.accounting.service.repository.HouseholdBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BankBookService {

    private final HouseholdBookRepository householdBookRepository;
    private final EmployeeRepository employeeRepository;
    private final BankBookRepository bankBookRepository;
    private final UserInfoService userInfoService;
    private final FarmAnimalRepository farmAnimalRepository;

    @Transactional
    public void createBankBook(BankBookCreateRequest request) {
        HouseholdBookEntity householdBook = householdBookRepository.findByNameAndKozhuunName(
                request.getHouseholdBookName(),
                request.getKozhuunName()
        );
        if (householdBook == null) {
            throw new RuntimeException("Похозяйственная книга не найдена");
        }
        BankBookEntity entity = new BankBookEntity();
        entity.setName(request.getName());
        entity.setHouseholdBook(householdBook);
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        entity.setAddress(request.getAddress());
        entity.setCadastralNumber(request.getCadastralNumber());
        entity.setInn(request.getInn());
        entity.setAdditionalInfo(request.getAdditionalInfo());
        bankBookRepository.save(entity);
    }

    @Transactional
    public BankBooksResponse getBankBooks(BankBookSearchRequest request) {
        return new BankBooksResponse(bankBookRepository.findAllByHouseholdBookNameAndHouseholdBookKozhuunName(
                request.getHouseholdBookName(),
                request.getKozhuunName()
        ));
    }

    @Transactional
    public void addFarmAnimals(AddFarmAnimalsRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        Map<String, FarmAnimalEntity> name2FarmAnimalMap = farmAnimalRepository.findAll().stream()
                .collect(Collectors.toMap(FarmAnimalEntity::getName, Function.identity(), (e1, e2) -> e1));
        List<BankBookToFarmAnimalEntity> farmAnimals = Optional.ofNullable(request.getAnimals())
                .orElse(Collections.emptyList())
                .stream()
                .map(animal -> new BankBookToFarmAnimalEntity(animal, bankBook, name2FarmAnimalMap))
                .peek(entity -> entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin())))
                .collect(Collectors.toList());
        bankBook.getFarmAnimals().addAll(farmAnimals);
    }

    @Transactional
    public BookFarmAnimalsResponse getAnimals(BankBookSpecifierRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        return new BookFarmAnimalsResponse(bankBook.getFarmAnimals());
    }

    private BankBookEntity getBankBookBySpecifiers(BankBookSpecifierRequest request) {
        return bankBookRepository.findByNameAndHouseholdBookNameAndHouseholdBookKozhuunName(
                request.getBankBookName(),
                request.getHouseholdBookName(),
                request.getKozhuunName()
        );
    }
}
