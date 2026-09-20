package com.railwaysystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "maintenance_records")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceId;

    @ManyToOne
    @JoinColumn(name = "fault_id", nullable = false)
    private FaultReport fault;

    @ManyToOne
    @JoinColumn(name = "performed_by_user_id", nullable = false)
    private User performedBy;

    private String maintenanceDetails;

    private String status;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(FaultReport fault,
                             User performedBy,
                             String maintenanceDetails,
                             String status) {
        this.fault = fault;
        this.performedBy = performedBy;
        this.maintenanceDetails = maintenanceDetails;
        this.status = status;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public FaultReport getFault() {
        return fault;
    }

    public void setFault(FaultReport fault) {
        this.fault = fault;
    }

    public User getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
    }

    public String getMaintenanceDetails() {
        return maintenanceDetails;
    }

    public void setMaintenanceDetails(String maintenanceDetails) {
        this.maintenanceDetails = maintenanceDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}