package com.railwaysystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "level_crossings")
public class LevelCrossing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crossingId;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private RailwayStation station;

    @Column(nullable = false, unique = true)
    private String crossingCode;

    private String location;

    private String crossingStatus;

    public LevelCrossing() {
    }

    public LevelCrossing(RailwayStation station, String crossingCode,
                         String location, String crossingStatus) {
        this.station = station;
        this.crossingCode = crossingCode;
        this.location = location;
        this.crossingStatus = crossingStatus;
    }

    public Long getCrossingId() {
        return crossingId;
    }

    public RailwayStation getStation() {
        return station;
    }

    public void setStation(RailwayStation station) {
        this.station = station;
    }

    public String getCrossingCode() {
        return crossingCode;
    }

    public void setCrossingCode(String crossingCode) {
        this.crossingCode = crossingCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCrossingStatus() {
        return crossingStatus;
    }

    public void setCrossingStatus(String crossingStatus) {
        this.crossingStatus = crossingStatus;
    }
}