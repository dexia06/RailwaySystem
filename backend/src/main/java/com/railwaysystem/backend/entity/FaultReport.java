package com.railwaysystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fault_reports")
public class FaultReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faultId;

    @ManyToOne
    @JoinColumn(name = "crossing_id", nullable = false)
    private LevelCrossing crossing;

    @ManyToOne
    @JoinColumn(name = "signal_id", nullable = false)
    private RailwaySignal signal;

    @ManyToOne
    @JoinColumn(name = "reported_by_user_id", nullable = false)
    private User reportedBy;

    private String faultType;

    private String severity;

    private String status;

    public FaultReport() {
    }

    public FaultReport(LevelCrossing crossing,
                       RailwaySignal signal,
                       User reportedBy,
                       String faultType,
                       String severity,
                       String status) {
        this.crossing = crossing;
        this.signal = signal;
        this.reportedBy = reportedBy;
        this.faultType = faultType;
        this.severity = severity;
        this.status = status;
    }

    public Long getFaultId() {
        return faultId;
    }

    public LevelCrossing getCrossing() {
        return crossing;
    }

    public void setCrossing(LevelCrossing crossing) {
        this.crossing = crossing;
    }

    public RailwaySignal getSignal() {
        return signal;
    }

    public void setSignal(RailwaySignal signal) {
        this.signal = signal;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
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
}