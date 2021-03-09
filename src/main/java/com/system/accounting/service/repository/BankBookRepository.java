package com.system.accounting.service.repository;

import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.ResidentEntity;
import com.system.accounting.model.entity.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Long> {

    @Query("select b as bankBook, r.name as mainFio " +
            "from BankBookEntity b " +
            "join ResidentEntity r on r.bankBook = b and r.relation is null " +
            "where b.householdBook.name = ?1 and b.householdBook.kozhuun.name = ?2")
    List<BankBookWithMainResident> findAllByHouseholdBookNameAndHouseholdBookKozhuunName(String household, String kozhuun);

    @Query("select b as bankBook, " +
            "r.name as residentName, " +
            "r.relation as relation, " +
            "b.householdBook.villageName as village, " +
            "f.name as animalName, " +
            "bf.value as animalCount, " +
            "l.cadastralNumber as cadastralNumber, " +
            "lc.name as landCategory, " +
            "l.totalArea as totalArea, " +
            "t as transport, " +
            "l.updateTime as landTime, " +
            "r.updateTime as residentTime, " +
            "t.updateTime as transportTime, " +
            "bf.creationDate as animalTime " +
            "from BankBookEntity b " +
            "left join ResidentEntity r on r.bankBook = b " +
            "left join PassportEntity p on p.resident = r " +
            "left join BankBookToFarmAnimalEntity bf on bf.bankBook = b " +
            "left join FarmAnimalEntity f on bf.farmAnimal = f " +
            "left join LandEntity l on l.bankBook = b " +
            "left join LandCategoryEntity lc on lc = l.landCategory " +
            "left join TransportEntity t on t.bankBook = b " +
            "where b.householdBook.kozhuun.name = ?1 " +
            "and r.cancelDate is null")
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

        String getResidentName();

        String getRelation();

        String getVillage();

        String getAnimalName();

        String getAnimalCount();

        String getCadastralNumber();

        String getLandCategory();

        Double getTotalArea();

        TransportEntity getTransport();

        LocalDateTime getResidentTime();

        LocalDateTime getLandTime();

        LocalDateTime getTransportTime();

        LocalDateTime getAnimalTime();
    }
}
