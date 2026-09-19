package com.railwaysystem.backend.service;

import com.railwaysystem.backend.entity.LevelCrossing;
import com.railwaysystem.backend.repository.LevelCrossingRepository;
import org.springframework.stereotype.Service;

@Service
public class SafetyService {

    private final LevelCrossingRepository repository;

    public SafetyService(LevelCrossingRepository repository) {
        this.repository = repository;
    }

    public LevelCrossing detectTrain(Long crossingId, int distance) {
        LevelCrossing crossing = repository.findById(crossingId)
                .orElseThrow(() -> new RuntimeException("Crossing not found"));

        crossing.setTrainStatus("TRAIN_DETECTED_" + distance + "KM");

        if (distance == 2) {
            if ("CLEAR".equalsIgnoreCase(crossing.getOccupancyStatus())) {
                crossing.setGateStatus("CLOSING");
                crossing.setSafetyStatus("WARNING");
            } else {
                crossing.setGateStatus("WARNING");
                crossing.setSafetyStatus("CRITICAL");
            }
        } else if (distance == 1) {
            if ("CLOSED".equalsIgnoreCase(crossing.getGateStatus())
                    && "CLEAR".equalsIgnoreCase(crossing.getOccupancyStatus())) {
                crossing.setSafetyStatus("SAFE");
            } else {
                crossing.setSafetyStatus("CRITICAL");
            }
        }

        return repository.save(crossing);
    }
}