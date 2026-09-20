package com.railwaysystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @ManyToOne
    @JoinColumn(name = "fault_id", nullable = false)
    private FaultReport fault;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String alertType;

    private String alertMessage;

    private String status;

    public Alert() {
    }

    public Alert(FaultReport fault,
                 User user,
                 String alertType,
                 String alertMessage,
                 String status) {
        this.fault = fault;
        this.user = user;
        this.alertType = alertType;
        this.alertMessage = alertMessage;
        this.status = status;
    }

    public Long getAlertId() {
        return alertId;
    }

    public FaultReport getFault() {
        return fault;
    }

    public void setFault(FaultReport fault) {
        this.fault = fault;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}