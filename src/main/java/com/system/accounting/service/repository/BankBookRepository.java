package com.system.accounting.service.repository;

import com.system.accounting.model.entity.BankBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Long> {

    @Query("select b as bankBook, r.name as mainFio " +
            "from BankBookEntity b " +
            "join ResidentEntity r on r.bankBook = b and r.relation is null " +
            "where b.householdBook.name = ?1 and b.householdBook.kozhuun.name = ?2")
    List<BankBookWithMainResident> findAllByHouseholdBookNameAndHouseholdBookKozhuunName(String household, String kozhuun);

    BankBookEntity findByNameAndHouseholdBookNameAndHouseholdBookKozhuunName(String name, String houseName, String kozhuunName);

    interface BankBookWithMainResident {

        BankBookEntity getBankBook();

        String getMainFio();
    }
}
