package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.FaultReport;
import com.railwaysystem.backend.entity.MaintenanceRecord;
import com.railwaysystem.backend.entity.User;
import com.railwaysystem.backend.repository.FaultReportRepository;
import com.railwaysystem.backend.repository.MaintenanceRecordRepository;
import com.railwaysystem.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "*")
public class MaintenanceRecordController {

    private final MaintenanceRecordRepository maintenanceRepository;
    private final FaultReportRepository faultRepository;
    private final UserRepository userRepository;

    public MaintenanceRecordController(
            MaintenanceRecordRepository maintenanceRepository,
            FaultReportRepository faultRepository,
            UserRepository userRepository) {

        this.maintenanceRepository = maintenanceRepository;
        this.faultRepository = faultRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRepository.findAll();
    }

    @GetMapping("/{id}")
    public MaintenanceRecord getMaintenanceRecord(@PathVariable Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));
    }

    @GetMapping("/fault/{faultId}")
    public List<MaintenanceRecord> getByFault(
            @PathVariable Long faultId) {
        return maintenanceRepository.findByFaultFaultId(faultId);
    }

    @GetMapping("/user/{userId}")
    public List<MaintenanceRecord> getByUser(
            @PathVariable Long userId) {
        return maintenanceRepository.findByPerformedById(userId);
    }

    @GetMapping("/status/{status}")
    public List<MaintenanceRecord> getByStatus(
            @PathVariable String status) {
        return maintenanceRepository.findByStatus(status);
    }

    @PostMapping("/fault/{faultId}/user/{userId}")
    public MaintenanceRecord createMaintenanceRecord(
            @PathVariable Long faultId,
            @PathVariable Long userId,
            @RequestBody MaintenanceRecord maintenanceRecord) {

        FaultReport fault = faultRepository.findById(faultId)
                .orElseThrow(() -> new RuntimeException("Fault not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        maintenanceRecord.setFault(fault);
        maintenanceRecord.setPerformedBy(user);

        return maintenanceRepository.save(maintenanceRecord);
    }

    @PutMapping("/{id}")
    public MaintenanceRecord updateMaintenanceRecord(
            @PathVariable Long id,
            @RequestBody MaintenanceRecord updatedRecord) {

        MaintenanceRecord record = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));

        record.setMaintenanceDetails(updatedRecord.getMaintenanceDetails());
        record.setStatus(updatedRecord.getStatus());

        return maintenanceRepository.save(record);
    }

    @DeleteMapping("/{id}")
    public String deleteMaintenanceRecord(@PathVariable Long id) {

        if (!maintenanceRepository.existsById(id)) {
            throw new RuntimeException("Maintenance record not found");
        }

        maintenanceRepository.deleteById(id);

        return "Maintenance record deleted successfully";
    }
}