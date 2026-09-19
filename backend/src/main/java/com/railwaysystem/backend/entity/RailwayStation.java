package com.railwaysystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "railway_stations")
public class RailwayStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;

    @Column(nullable = false)
    private String stationName;

    @Column(nullable = false, unique = true)
    private String stationCode;

    private String zone;

    public RailwayStation() {
    }

    public RailwayStation(String stationName, String stationCode, String zone) {
        this.stationName = stationName;
        this.stationCode = stationCode;
        this.zone = zone;
    }

    public Long getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}