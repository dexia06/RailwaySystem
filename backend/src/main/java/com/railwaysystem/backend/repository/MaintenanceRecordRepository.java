package com.railwaysystem.backend.repository;

import com.railwaysystem.backend.entity.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {

    List<MaintenanceRecord> findByFaultFaultId(Long faultId);

    List<MaintenanceRecord> findByPerformedById(Long userId);

    List<MaintenanceRecord> findByStatus(String status);
}