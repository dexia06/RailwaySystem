package com.railwaysystem.backend.repository;

import com.railwaysystem.backend.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByFaultFaultId(Long faultId);

    List<Alert> findByUserId(Long userId);

    List<Alert> findByStatus(String status);
}