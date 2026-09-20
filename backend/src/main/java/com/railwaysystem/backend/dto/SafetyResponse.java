package com.railwaysystem.backend.dto;

public class SafetyResponse {

    private String crossingName;
    private String location;
    private String trainStatus;
    private String gateStatus;
    private String occupancyStatus;
    private String safetyStatus;

    public SafetyResponse() {
    }

    public SafetyResponse(
            String crossingName,
            String location,
            String trainStatus,
            String gateStatus,
            String occupancyStatus,
            String safetyStatus) {

        this.crossingName = crossingName;
        this.location = location;
        this.trainStatus = trainStatus;
        this.gateStatus = gateStatus;
        this.occupancyStatus = occupancyStatus;
        this.safetyStatus = safetyStatus;
    }

    public String getCrossingName() {
        return crossingName;
    }

    public String getLocation() {
        return location;
    }

    public String getTrainStatus() {
        return trainStatus;
    }

    public String getGateStatus() {
        return gateStatus;
    }

    public String getOccupancyStatus() {
        return occupancyStatus;
    }

    public String getSafetyStatus() {
        return safetyStatus;
    }
}