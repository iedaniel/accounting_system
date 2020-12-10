package com.system.accounting.service.repository;

import com.system.accounting.model.entity.LandTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandTypeRepository extends JpaRepository<LandTypeEntity, Long> {

    LandTypeEntity findByName(String name);
}
