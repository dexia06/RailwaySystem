package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.LevelCrossing;
import com.railwaysystem.backend.repository.LevelCrossingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crossings")
@CrossOrigin(origins = "*")
public class LevelCrossingController {

    private final LevelCrossingRepository repository;

    public LevelCrossingController(LevelCrossingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LevelCrossing> getAllCrossings() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public LevelCrossing getCrossingById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public LevelCrossing createCrossing(@RequestBody LevelCrossing crossing) {
        return repository.save(crossing);
    }

    @PutMapping("/{id}")
    public LevelCrossing updateCrossing(
            @PathVariable Long id,
            @RequestBody LevelCrossing crossing) {

        return repository.findById(id)
                .map(existing -> {
                    existing.setCrossingName(crossing.getCrossingName());
                    existing.setLocation(crossing.getLocation());
                    existing.setGateStatus(crossing.getGateStatus());
                    existing.setOccupancyStatus(crossing.getOccupancyStatus());
                    existing.setTrainStatus(crossing.getTrainStatus());
                    existing.setSafetyStatus(crossing.getSafetyStatus());

                    return repository.save(existing);
                })
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteCrossing(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return "Crossing not found";
        }

        repository.deleteById(id);
        return "Crossing deleted successfully";
    }
}