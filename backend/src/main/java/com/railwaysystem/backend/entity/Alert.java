package com.railwaysystem.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long crossingId;
    private String alertType;
    private String message;
    private String severity;
    private String status;
    private LocalDateTime createdAt;

    public Alert() {
    }

    public Alert(Long crossingId, String alertType, String message,
                 String severity, String status, LocalDateTime createdAt) {
        this.crossingId = crossingId;
        this.alertType = alertType;
        this.message = message;
        this.severity = severity;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCrossingId() {
        return crossingId;
    }

    public void setCrossingId(Long crossingId) {
        this.crossingId = crossingId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}