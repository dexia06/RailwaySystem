package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.LevelCrossing;
import com.railwaysystem.backend.repository.LevelCrossingRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/occupancy")
@CrossOrigin(origins = "*")
public class OccupancyController {

    private final LevelCrossingRepository repository;

    public OccupancyController(LevelCrossingRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/{crossingId}/{status}")
    public LevelCrossing updateOccupancy(
            @PathVariable Long crossingId,
            @PathVariable String status) {

        LevelCrossing crossing = repository.findById(crossingId)
                .orElseThrow(() -> new RuntimeException("Crossing not found"));

        if (!status.equalsIgnoreCase("CLEAR")
                && !status.equalsIgnoreCase("OCCUPIED")) {
            throw new IllegalArgumentException(
                    "Occupancy status must be CLEAR or OCCUPIED");
        }

        crossing.setOccupancyStatus(status.toUpperCase());

        return repository.save(crossing);
    }
}