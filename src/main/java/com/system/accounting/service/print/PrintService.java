package com.system.accounting.service.print;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import com.system.accounting.model.dto.bank_book.lands.LandSpecifierRequest;
import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.LandEntity;
import com.system.accounting.model.entity.PassportEntity;
import com.system.accounting.model.entity.ResidentEntity;
import com.system.accounting.service.bank_book.BankBookService;
import com.system.accounting.service.repository.FarmAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Component
@RequiredArgsConstructor
public class PrintService {

    private final BankBookService bankBookService;
    private final FarmAnimalRepository farmAnimalRepository;

    @Transactional
    public Map<String, Object> printBankBook(LandSpecifierRequest request) {
        Map<String, Object> model = new HashMap<>();
        BankBookEntity bankBook = bankBookService.getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        LandEntity land = bankBook.getLands().stream()
                .filter(e -> e.getCadastralNumber().equals(request.getCadastralNumber()))
                .findFirst()
                .orElse(null);
        if (land == null) {
            throw new RuntimeException("Не найден земельный участок");
        }
        Optional<ResidentEntity> residentOpt = Optional.ofNullable(bankBook.mainResident());
        putCarefully(model, "resident_fio", residentOpt.map(ResidentEntity::getName).orElse(null));
        putCarefully(model, "dd", residentOpt.map(ResidentEntity::getBirthDate).map(LocalDate::getDayOfMonth).orElse(null));
        putCarefully(model, "month", residentOpt.map(ResidentEntity::getBirthDate).map(LocalDate::getMonth).map(m -> m.getDisplayName(TextStyle.SHORT, new Locale("ru"))).orElse(null));
        putCarefully(model, "year", residentOpt.map(ResidentEntity::getBirthDate).map(LocalDate::getYear).orElse(null));
        putCarefully(model, "current_date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        Optional<PassportEntity> passportOpt = residentOpt.map(ResidentEntity::getPassport);
        putCarefully(model, "docType", passportOpt.map(__ -> "паспорт").orElse(null));
        putCarefully(model, "passportSeries", passportOpt.map(PassportEntity::getPassportSeries).orElse(null));
        putCarefully(model, "passportId", passportOpt.map(PassportEntity::getPassportId).orElse(null));
        putCarefully(model, "issueDay", passportOpt.map(PassportEntity::getIssueDate).map(LocalDate::getDayOfMonth).orElse(null));
        putCarefully(model, "issueMonth", passportOpt.map(PassportEntity::getIssueDate).map(LocalDate::getMonth).map(m -> m.getDisplayName(TextStyle.SHORT, new Locale("ru"))).orElse(null));
        putCarefully(model, "issueYear", passportOpt.map(PassportEntity::getIssueDate).map(LocalDate::getYear).orElse(null));
        putCarefully(model, "authority", passportOpt.map(PassportEntity::getIssuingAuthority).orElse(null));
        putCarefully(model, "address", Optional.ofNullable(bankBook.getAddress()).orElse(null));
        putCarefully(model, "rights", Optional.ofNullable(land.getDocument()).orElse(null));
        putCarefully(model, "totalArea", Optional.ofNullable(land.getTotalArea()).orElse(null));
        putCarefully(model, "landCategory", Optional.ofNullable(land.getLandCategory().getName()).orElse(null));
        return model;
    }

    @Transactional
    public Map<String, Object> printBankBookSummary(BankBookSpecifierRequest request) {
        Map<String, Object> model = new HashMap<>();
        BankBookEntity bankBook = bankBookService.getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        List<FarmAnimalRepository.BBSummary> bbSummaryAnimals = farmAnimalRepository.findBBSummary(bankBook.getId());
        model.put("animals", bbSummaryAnimals);
        model.put("sumon", bankBook.getHouseholdBook().getVillageName());
        model.put("kozhuun", bankBook.getHouseholdBook().getKozhuun().getName());
        model.put("householdBook", bankBook.getHouseholdBook().getName());
        String mainResidentName = Optional.ofNullable(bankBook.mainResident())
                .map(ResidentEntity::getName)
                .orElse("");
        model.put("mainResident", mainResidentName);
        model.put("bankBook", bankBook.getName());
        return model;
    }

    private void putCarefully(Map<String, Object> map, String key, Object obj) {
        if (obj == null) {
            map.put(key, "");
            return;
        }
        map.put(key, obj);
    }
}
