package com.system.accounting.service.repository;

import com.system.accounting.model.entity.LandCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandCategoryRepository extends JpaRepository<LandCategoryEntity, Long> {

    LandCategoryEntity findByName(String name);
}
