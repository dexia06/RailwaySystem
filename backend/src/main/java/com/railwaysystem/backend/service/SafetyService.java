package com.railwaysystem.backend.service;

import com.railwaysystem.backend.dto.SafetyResponse;
import com.railwaysystem.backend.entity.LevelCrossing;
import com.railwaysystem.backend.repository.LevelCrossingRepository;
import org.springframework.stereotype.Service;

@Service
public class SafetyService {

    private final LevelCrossingRepository repository;

    public SafetyService(LevelCrossingRepository repository) {
        this.repository = repository;
    }

    public SafetyResponse detectTrain(Long crossingId, int distance) {

        LevelCrossing crossing = repository.findById(crossingId)
                .orElseThrow(() -> new RuntimeException("Crossing not found"));

        String trainStatus;
        String gateStatus;
        String occupancyStatus;
        String safetyStatus;

        if (distance == 2) {
            crossing.setCrossingStatus("TRAIN_DETECTED_2KM");

            trainStatus = "TRAIN DETECTED - 2 KM";
            gateStatus = "CLOSED";
            occupancyStatus = "CLEAR";
            safetyStatus = "WARNING";

        } else if (distance == 1) {
            crossing.setCrossingStatus("TRAIN_DETECTED_1KM");

            trainStatus = "TRAIN DETECTED - 1 KM";
            gateStatus = "CLOSED";
            occupancyStatus = "CLEAR";
            safetyStatus = "SAFE";

        } else {
            crossing.setCrossingStatus("TRAIN_DETECTED");

            trainStatus = "TRAIN DETECTED";
            gateStatus = "CLOSED";
            occupancyStatus = "CLEAR";
            safetyStatus = "SAFE";
        }

        repository.save(crossing);

        return new SafetyResponse(
                crossing.getCrossingCode(),
                crossing.getLocation(),
                trainStatus,
                gateStatus,
                occupancyStatus,
                safetyStatus
        );
    }
}