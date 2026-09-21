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
        String occupancyStatus = "CLEAR";
        String safetyStatus;

        if (distance == 2) {

            trainStatus = "TRAIN DETECTED - 2 KM";
            gateStatus = "CLOSED";
            safetyStatus = "WARNING";

        } else if (distance == 1) {

            trainStatus = "TRAIN DETECTED - 1 KM";

            if ("GATE_CLOSED".equals(crossing.getCrossingStatus())) {

                gateStatus = "CLOSED";
                safetyStatus = "SAFE";

            } else {

                gateStatus = "OPEN";
                safetyStatus = "CRITICAL";
            }

        } else {

            trainStatus = "TRAIN DETECTED";
            gateStatus = "CLOSED";
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