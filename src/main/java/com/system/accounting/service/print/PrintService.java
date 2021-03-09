package com.system.accounting.service.print;

import com.google.common.util.concurrent.AtomicDouble;
import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import com.system.accounting.model.dto.bank_book.lands.LandSpecifierRequest;
import com.system.accounting.model.dto.bank_book.residents.ResidentDto;
import com.system.accounting.model.entity.*;
import com.system.accounting.service.bank_book.BankBookService;
import com.system.accounting.service.repository.FarmAnimalRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

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
        putCarefully(model, "householdBook", bankBook.getHouseholdBook().getName());
        putCarefully(model, "bankBook", bankBook.getName());
        putCarefully(model, "village", bankBook.getHouseholdBook().getVillageName());
        putCarefully(model, "kozhuun", bankBook.getHouseholdBook().getKozhuun().getName());
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
        model.put("mainResident", Optional.of(mainResidentName).orElse(""));
        model.put("bankBook", bankBook.getName());
        model.put("address", Optional.ofNullable(bankBook.getAddress()).orElse(""));
        List<ResidentEntity> residents = bankBook.getResidents().stream()
                .filter(e -> e.getCancelDate() != null)
                .sorted(Comparator.comparing(ResidentEntity::getRelation, (e1, e2) -> e1 == null ? -1 : 1))
                .collect(Collectors.toList());
        model.put("residents", residents);
        List<TransportEntity> transport = bankBook.getTransport();
        model.put("transport", transport);
        List<LandEntity> lands = bankBook.getLands();
        lands.forEach(land -> land.setCategoryName(land.getLandCategory().getName()));
        model.put("landsCnt", lands.size());
        double sum = lands.stream()
                .mapToDouble(LandEntity::getTotalArea)
                .sum();
        model.put("landsSum", sum);
        model.put("landCategory", lands);
        Map<LandTypeEntity, AtomicDouble> landType2Area = new HashMap<>();
        lands.stream()
                .map(LandEntity::getLandTypes)
                .flatMap(Collection::stream)
                .forEach(e -> {
                    landType2Area.computeIfAbsent(e.getLandType(), key -> new AtomicDouble(0)).getAndAdd(e.getValue());
                });
        List<LandTypeEntry> landTypes = landType2Area.entrySet().stream()
                .map(e -> new LandTypeEntry(e.getKey().getName(), e.getValue().get()))
                .collect(Collectors.toList());
        model.put("landTypes", landTypes);
        Map<AgricultureEntity, AtomicDouble> agriculture2Area = new HashMap<>();
        lands.stream()
                .map(LandEntity::getAgricultures)
                .flatMap(Collection::stream)
                .forEach(e -> {
                    agriculture2Area.computeIfAbsent(e.getAgriculture(), key -> new AtomicDouble(0)).getAndAdd(e.getArea());
                });
        List<LandTypeEntry> agricultures = agriculture2Area.entrySet().stream()
                .map(e -> new LandTypeEntry(e.getKey().getName(), e.getValue().get()))
                .collect(Collectors.toList());
        model.put("agricultures", agricultures);
        model.put("hasTransport", !transport.isEmpty());
        model.put("hasLand", !lands.isEmpty());
        model.put("hasAnimals", !bbSummaryAnimals.isEmpty());
        LocalDate now = LocalDate.now();
        model.put("curDay", now.getDayOfMonth());
        model.put("curMonth", now.getMonth().getDisplayName(TextStyle.FULL, new Locale("ru")));
        model.put("curYear", now.getYear());
        return model;
    }

    private void putCarefully(Map<String, Object> map, String key, Object obj) {
        if (obj == null) {
            map.put(key, "");
            return;
        }
        map.put(key, obj);
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    static class LandTypeEntry {
        private final String name;
        private final double area;
    }
}
