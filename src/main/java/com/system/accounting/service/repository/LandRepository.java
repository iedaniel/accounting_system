package com.system.accounting.service.repository;

import com.system.accounting.model.entity.BankBookEntity;
import com.system.accounting.model.entity.LandEntity;
import com.system.accounting.model.entity.LandTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandRepository extends JpaRepository<LandEntity, Long> {

    LandEntity findByBankBookAndCadastralNumber(BankBookEntity bankBook, String cadastralNumber);
}
