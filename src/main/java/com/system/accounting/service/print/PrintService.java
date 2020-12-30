package com.system.accounting.service.print;

import com.system.accounting.model.dto.bank_book.BankBookSpecifierRequest;
import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.ResidentEntity;
import com.system.accounting.service.bank_book.BankBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PrintService {

    private final BankBookService bankBookService;

    @Transactional
    public Map<String, Object> printBankBook(BankBookSpecifierRequest request) {
        Map<String, Object> model = new HashMap<>();
        BankBookEntity bankBook = bankBookService.getBankBookBySpecifiers(request);
        if (bankBook == null) {
            throw new RuntimeException("Не найден лицевой счёт");
        }
        ResidentEntity resident = bankBook.mainResident();
        putCarefully(model, "resident_fio", resident.getName());
        putCarefully(model, "dd", Optional.ofNullable(resident.getBirthDate()).map(LocalDate::getDayOfMonth).orElse(null));
        putCarefully(model, "month", Optional.ofNullable(resident.getBirthDate()).map(LocalDate::getMonth).orElse(null));
        putCarefully(model, "year", Optional.ofNullable(resident.getBirthDate()).map(LocalDate::getYear).orElse(null));
        putCarefully(model, "current_date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
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
