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

        if (distance == 2) {
            crossing.setCrossingStatus("TRAIN_DETECTED_2KM");
        } else if (distance == 1) {
            crossing.setCrossingStatus("TRAIN_DETECTED_1KM");
        } else {
            crossing.setCrossingStatus("TRAIN_DETECTED");
        }

        return repository.save(crossing);
    }
}