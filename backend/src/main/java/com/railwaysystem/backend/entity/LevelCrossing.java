package com.railwaysystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "level_crossings")
public class LevelCrossing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String crossingName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String gateStatus;

    @Column(nullable = false)
    private String occupancyStatus;

    @Column(nullable = false)
    private String trainStatus;

    @Column(nullable = false)
    private String safetyStatus;

    public LevelCrossing() {
    }

    public LevelCrossing(String crossingName, String location,
                          String gateStatus, String occupancyStatus,
                          String trainStatus, String safetyStatus) {
        this.crossingName = crossingName;
        this.location = location;
        this.gateStatus = gateStatus;
        this.occupancyStatus = occupancyStatus;
        this.trainStatus = trainStatus;
        this.safetyStatus = safetyStatus;
    }

    public Long getId() {
        return id;
    }

    public String getCrossingName() {
        return crossingName;
    }

    public void setCrossingName(String crossingName) {
        this.crossingName = crossingName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGateStatus() {
        return gateStatus;
    }

    public void setGateStatus(String gateStatus) {
        this.gateStatus = gateStatus;
    }

    public String getOccupancyStatus() {
        return occupancyStatus;
    }

    public void setOccupancyStatus(String occupancyStatus) {
        this.occupancyStatus = occupancyStatus;
    }

    public String getTrainStatus() {
        return trainStatus;
    }

    public void setTrainStatus(String trainStatus) {
        this.trainStatus = trainStatus;
    }

    public String getSafetyStatus() {
        return safetyStatus;
    }

    public void setSafetyStatus(String safetyStatus) {
        this.safetyStatus = safetyStatus;
    }
}