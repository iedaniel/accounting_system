package com.system.accounting.service.repository;

import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.ResidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResidentRepository extends JpaRepository<ResidentEntity, Long> {

    @Query("select r " +
            "from ResidentEntity r " +
            "where r.bankBook = ?1 and r.cancelDate is null")
    List<ResidentEntity> getActiveResidents(BankBookEntity bankBookEntity);
}
