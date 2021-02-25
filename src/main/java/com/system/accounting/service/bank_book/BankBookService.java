package com.system.accounting.service.bank_book;

import com.system.accounting.model.dto.bank_book.*;
import com.system.accounting.model.dto.bank_book.farm_animals.AddFarmAnimalsRequest;
import com.system.accounting.model.dto.bank_book.farm_animals.BookFarmAnimalsResponse;
import com.system.accounting.model.dto.bank_book.lands.LandCreateRequest;
import com.system.accounting.model.dto.bank_book.lands.LandResponse;
import com.system.accounting.model.dto.bank_book.lands.LandSpecifierRequest;
import com.system.accounting.model.dto.bank_book.lands.LandsResponse;
import com.system.accounting.model.dto.bank_book.lands.agricultures.AddAgriculturesRequest;
import com.system.accounting.model.dto.bank_book.lands.land_types.AddLandTypesRequest;
import com.system.accounting.model.dto.bank_book.residents.AddResidentsRequest;
import com.system.accounting.model.dto.bank_book.residents.BookResidentsResponse;
import com.system.accounting.model.dto.bank_book.residents.CancelResidentRequest;
import com.system.accounting.model.dto.bank_book.transport.AllTransportResponse;
import com.system.accounting.model.dto.bank_book.transport.TransportCreateRequest;
import com.system.accounting.model.entity.*;
import com.system.accounting.service.UserInfoService;
import com.system.accounting.service.repository.*;
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
    private final LandTypeRepository landTypeRepository;
    private final LandRepository landRepository;
    private final AgricultureRepository agricultureRepository;
    private final LandCategoryRepository landCategoryRepository;
    private final ResidentRepository residentRepository;

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
        entity.setCreationDate(request.getCreationDate());
        entity.setName(request.getName());
        entity.setHouseholdBook(householdBook);
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        entity.setAddress(request.getAddress());
        entity.setInn(request.getInn());
        entity.setAdditionalInfo(request.getAdditionalInfo());
        bankBookRepository.save(entity);
    }

    @Transactional
    public void closeBankBook(BankBookCloseRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        bankBook.setClosingDate(request.getCloseDate());
        bankBook.setClosingReason(request.getCloseReason());
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
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        return new BookFarmAnimalsResponse(bankBook.getFarmAnimals());
    }

    @Transactional
    public void addResidents(AddResidentsRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        List<ResidentEntity> residents = request.getResidents().stream()
                .map(resident -> new ResidentEntity(resident, bankBook))
                .peek(e -> {
                    e.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
                    if (e.getPassport() != null) {
                        e.getPassport().setResident(e);
                        e.getPassport().setCreator(e.getCreator());
                    }
                })
                .collect(Collectors.toList());
        bankBook.getResidents().addAll(residents);
    }

    @Transactional
    public void cancelResident(CancelResidentRequest request) {
        residentRepository.findById(request.getId())
                .ifPresent(entity -> {
                    entity.setCancelDate(request.getCancelDate());
                    entity.setCancelReason(request.getCancelReason());
                });
    }

    @Transactional
    public BookResidentsResponse getResidents(BankBookSpecifierRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        return new BookResidentsResponse(bankBook.getResidents());
    }

    @Transactional
    public void addLand(LandCreateRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        LandCategoryEntity landCategory = landCategoryRepository.findByName(request.getLandCategory());
        if (landCategory == null) {
            throw new RuntimeException("Не удалось найти указанную земельную категорию");
        }
        LandEntity entity = new LandEntity();
        entity.setBankBook(bankBook);
        entity.setCadastralNumber(request.getCadastralNumber());
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        entity.setDocument(request.getDocument());
        entity.setDocumentEndDate(request.getDocumentEndDate());
        entity.setLandCategory(landCategory);
        entity.setTotalArea(request.getTotalArea());
        bankBook.getLands().add(entity);
    }

    @Transactional
    public LandsResponse getLands(BankBookSpecifierRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        return new LandsResponse(bankBook.getLands());
    }

    @Transactional
    public void addLandTypes(AddLandTypesRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        LandEntity land = landRepository.findByBankBookAndCadastralNumber(bankBook, request.getLand());
        EmployeeEntity creator = employeeRepository.findByLogin(userInfoService.currentUserLogin());
        List<BankBookToLandTypeEntity> landTypes = request.getLandTypes().stream()
                .map(landType -> {
                    LandTypeEntity landTypeEntity = landTypeRepository.findByName(landType.getLandType());
                    BankBookToLandTypeEntity entity = new BankBookToLandTypeEntity();
                    entity.setLand(land);
                    entity.setLandType(landTypeEntity);
                    entity.setValue(landType.getValue());
                    entity.setCreator(creator);
                    return entity;
                })
                .collect(Collectors.toList());
        land.getLandTypes().addAll(landTypes);
    }

    @Transactional
    public void addAgricultures(AddAgriculturesRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        LandEntity land = landRepository.findByBankBookAndCadastralNumber(bankBook, request.getLand());
        EmployeeEntity creator = employeeRepository.findByLogin(userInfoService.currentUserLogin());
        List<LandToAgricultureEntity> agricultures = request.getAgricultures().stream()
                .map(agricultureDto -> {
                    AgricultureEntity agriculture = agricultureRepository.findByName(agricultureDto.getAgriculture());
                    LandToAgricultureEntity entity = new LandToAgricultureEntity();
                    entity.setLand(land);
                    entity.setAgriculture(agriculture);
                    entity.setCreator(creator);
                    entity.setArea(agricultureDto.getValue());
                    return entity;
                })
                .collect(Collectors.toList());
        land.getAgricultures().addAll(agricultures);
    }

    @Transactional
    public LandResponse getLand(LandSpecifierRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        LandEntity land = landRepository.findByBankBookAndCadastralNumber(bankBook, request.getCadastralNumber());
        if (land == null) {
            throw new RuntimeException("Не найден земельный участок");
        }
        return new LandResponse(land);
    }

    @Transactional
    public BankBookResponse get(BankBookSpecifierRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        return new BankBookResponse(bankBook, bankBook.mainResident().getName());
    }

    @Transactional
    public AllTransportResponse getTransport(BankBookSpecifierRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        List<TransportEntity> transport = bankBook.getTransport();
        return new AllTransportResponse(transport);
    }

    @Transactional
    public void addTransport(TransportCreateRequest request) {
        BankBookEntity bankBook = getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        TransportEntity entity = new TransportEntity();
        entity.setName(request.getName());
        entity.setYear(request.getYear());
        entity.setNum(request.getNum());
        entity.setRights(request.getRights());
        entity.setBankBook(bankBook);
        entity.setCreator(employeeRepository.findByLogin(userInfoService.currentUserLogin()));
        bankBook.getTransport().add(entity);
    }

    public BankBookEntity getBankBookBySpecifiers(BankBookSpecifierRequest request) {
        return bankBookRepository.findByNameAndHouseholdBookNameAndHouseholdBookKozhuunName(
                request.getBankBookName(),
                request.getHouseholdBookName(),
                request.getKozhuunName()
        );
    }
}
