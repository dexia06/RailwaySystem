package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.LevelCrossing;
import com.railwaysystem.backend.repository.LevelCrossingRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gate")
@CrossOrigin(origins = "*")
public class GateController {

    private final LevelCrossingRepository repository;

    public GateController(LevelCrossingRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/close/{crossingId}")
    public LevelCrossing closeGate(@PathVariable Long crossingId) {

        LevelCrossing crossing = repository.findById(crossingId)
                .orElseThrow(() -> new RuntimeException("Crossing not found"));

        crossing.setCrossingStatus("GATE_CLOSED");

        return repository.save(crossing);
    }
}