package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.Alert;
import com.railwaysystem.backend.entity.FaultReport;
import com.railwaysystem.backend.entity.User;
import com.railwaysystem.backend.repository.AlertRepository;
import com.railwaysystem.backend.repository.FaultReportRepository;
import com.railwaysystem.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class AlertController {

    private final AlertRepository alertRepository;
    private final FaultReportRepository faultRepository;
    private final UserRepository userRepository;

    public AlertController(
            AlertRepository alertRepository,
            FaultReportRepository faultRepository,
            UserRepository userRepository) {

        this.alertRepository = alertRepository;
        this.faultRepository = faultRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @GetMapping("/{id}")
    public Alert getAlert(@PathVariable Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
    }

    @GetMapping("/fault/{faultId}")
    public List<Alert> getAlertsByFault(
            @PathVariable Long faultId) {
        return alertRepository.findByFaultFaultId(faultId);
    }

    @GetMapping("/user/{userId}")
    public List<Alert> getAlertsByUser(
            @PathVariable Long userId) {
        return alertRepository.findByUserId(userId);
    }

    @GetMapping("/status/{status}")
    public List<Alert> getAlertsByStatus(
            @PathVariable String status) {
        return alertRepository.findByStatus(status);
    }

    @PostMapping("/fault/{faultId}/user/{userId}")
    public Alert createAlert(
            @PathVariable Long faultId,
            @PathVariable Long userId,
            @RequestBody Alert alert) {

        FaultReport fault = faultRepository.findById(faultId)
                .orElseThrow(() -> new RuntimeException("Fault not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        alert.setFault(fault);
        alert.setUser(user);

        return alertRepository.save(alert);
    }

    @PutMapping("/{id}")
    public Alert updateAlert(
            @PathVariable Long id,
            @RequestBody Alert updatedAlert) {

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setAlertType(updatedAlert.getAlertType());
        alert.setAlertMessage(updatedAlert.getAlertMessage());
        alert.setStatus(updatedAlert.getStatus());

        return alertRepository.save(alert);
    }

    @DeleteMapping("/{id}")
    public String deleteAlert(@PathVariable Long id) {

        if (!alertRepository.existsById(id)) {
            throw new RuntimeException("Alert not found");
        }

        alertRepository.deleteById(id);

        return "Alert deleted successfully";
    }
}