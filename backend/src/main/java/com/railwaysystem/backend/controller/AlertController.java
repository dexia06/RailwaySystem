package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.Alert;
import com.railwaysystem.backend.repository.AlertRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @GetMapping("/crossing/{crossingId}")
    public List<Alert> getCrossingAlerts(@PathVariable Long crossingId) {
        return alertRepository.findByCrossingIdOrderByCreatedAtDesc(crossingId);
    }

    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        if (alert.getCreatedAt() == null) {
            alert.setCreatedAt(LocalDateTime.now());
        }

        if (alert.getStatus() == null || alert.getStatus().isBlank()) {
            alert.setStatus("ACTIVE");
        }

        return alertRepository.save(alert);
    }
}