package com.railwaysystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "railway_signals")
public class RailwaySignal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long signalId;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private RailwayStation station;

    @Column(nullable = false)
    private String signalCode;

    private String signalType;

    private String currentStatus;

    public RailwaySignal() {
    }

    public RailwaySignal(RailwayStation station, String signalCode,
                         String signalType, String currentStatus) {
        this.station = station;
        this.signalCode = signalCode;
        this.signalType = signalType;
        this.currentStatus = currentStatus;
    }

    public Long getSignalId() {
        return signalId;
    }

    public RailwayStation getStation() {
        return station;
    }

    public void setStation(RailwayStation station) {
        this.station = station;
    }

    public String getSignalCode() {
        return signalCode;
    }

    public void setSignalCode(String signalCode) {
        this.signalCode = signalCode;
    }

    public String getSignalType() {
        return signalType;
    }

    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}