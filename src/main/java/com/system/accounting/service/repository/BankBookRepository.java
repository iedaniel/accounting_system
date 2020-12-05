package com.system.accounting.service.repository;

import com.system.accounting.model.entity.BankBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Long> {

    List<BankBookEntity> findAllByHouseholdBookNameAndHouseholdBookKozhuunName(String household, String kozhuun);
}
