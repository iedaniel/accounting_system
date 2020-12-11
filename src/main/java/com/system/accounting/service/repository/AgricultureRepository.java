package com.system.accounting.service.repository;

import com.system.accounting.model.entity.AgricultureEntity;
import com.system.accounting.model.entity.FarmAnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgricultureRepository extends JpaRepository<AgricultureEntity, Long> {

    AgricultureEntity findByName(String name);
}
