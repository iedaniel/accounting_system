package com.system.accounting.service.repository;

import com.system.accounting.model.entity.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepository extends JpaRepository<TransportEntity, Long> {
}
