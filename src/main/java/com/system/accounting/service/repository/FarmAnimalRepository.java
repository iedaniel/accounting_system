package com.system.accounting.service.repository;

import com.system.accounting.model.entity.FarmAnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmAnimalRepository extends JpaRepository<FarmAnimalEntity, Long> {

    FarmAnimalEntity findByName(String name);
}
