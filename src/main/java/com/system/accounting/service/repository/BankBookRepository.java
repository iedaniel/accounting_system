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

    @Query("select b as bankBook, r.name as mainFio, b.householdBook.villageName as village, f.name as animalName, bf.value as animalCount " +
            "from BankBookEntity b " +
            "join ResidentEntity r on r.bankBook = b and r.relation is null " +
            "join BankBookToFarmAnimalEntity bf on bf.bankBook = b " +
            "join FarmAnimalEntity f on bf.farmAnimal = f " +
            "where b.householdBook.kozhuun.name = ?1")
    List<BankBookWithInfo> findAllByKozhuunName(String kozhuun);

    BankBookEntity findByNameAndHouseholdBookNameAndHouseholdBookKozhuunName(String name, String houseName, String kozhuunName);

    @Query("select distinct " +
            "b          as bankBook, " +
            "r.name     as mainFio " +
            "from BankBookEntity b " +
            "join ResidentEntity r on r.bankBook = b " +
            "where lower(b.name) like ?1 " +
            "or lower(b.householdBook.name) like ?1 " +
            "or lower(b.householdBook.kozhuun.name) like ?1 " +
            "or lower(b.householdBook.villageName) like ?1 " +
            "or lower(r.name) like ?1")
    List<BankBookWithMainResident> findAllByQuery(String query);

    interface BankBookWithMainResident {

        BankBookEntity getBankBook();

        String getMainFio();
    }

    interface BankBookWithInfo {

        BankBookEntity getBankBook();

        String getMainFio();

        String getVillage();

        String getAnimalName();

        String getAnimalCount();
    }
}
