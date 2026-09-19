package com.railwaysystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainId;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private RailwayStation station;

    @Column(nullable = false)
    private String trainNumber;

    private String trainName;

    private String currentStatus;

    public Train() {
    }

    public Train(RailwayStation station, String trainNumber,
                 String trainName, String currentStatus) {
        this.station = station;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.currentStatus = currentStatus;
    }

    public Long getTrainId() {
        return trainId;
    }

    public RailwayStation getStation() {
        return station;
    }

    public void setStation(RailwayStation station) {
        this.station = station;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}