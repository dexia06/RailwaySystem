package com.railwaysystem.backend.repository;

import com.railwaysystem.backend.entity.FaultReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaultReportRepository extends JpaRepository<FaultReport, Long> {

    List<FaultReport> findByCrossingCrossingId(Long crossingId);

    List<FaultReport> findByStatus(String status);
}