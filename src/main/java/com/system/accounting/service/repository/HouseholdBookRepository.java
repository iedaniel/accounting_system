package com.system.accounting.service.repository;

import com.system.accounting.model.entity.HouseholdBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdBookRepository extends JpaRepository<HouseholdBookEntity, Long> {
}
