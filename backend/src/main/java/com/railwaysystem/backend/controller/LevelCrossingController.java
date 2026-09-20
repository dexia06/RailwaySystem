package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.LevelCrossing;
import com.railwaysystem.backend.entity.RailwayStation;
import com.railwaysystem.backend.repository.LevelCrossingRepository;
import com.railwaysystem.backend.repository.RailwayStationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crossings")
@CrossOrigin(origins = "*")
public class LevelCrossingController {

    private final LevelCrossingRepository crossingRepository;
    private final RailwayStationRepository stationRepository;

    public LevelCrossingController(
            LevelCrossingRepository crossingRepository,
            RailwayStationRepository stationRepository) {
        this.crossingRepository = crossingRepository;
        this.stationRepository = stationRepository;
    }

    @GetMapping
    public List<LevelCrossing> getAllCrossings() {
        return crossingRepository.findAll();
    }

    @GetMapping("/{id}")
    public LevelCrossing getCrossing(@PathVariable Long id) {
        return crossingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crossing not found"));
    }

    @GetMapping("/station/{stationId}")
    public List<LevelCrossing> getCrossingsByStation(
            @PathVariable Long stationId) {
        return crossingRepository.findByStationStationId(stationId);
    }

    @PostMapping("/station/{stationId}")
    public LevelCrossing createCrossing(
            @PathVariable Long stationId,
            @RequestBody LevelCrossing crossing) {

        RailwayStation station = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        crossing.setStation(station);

        return crossingRepository.save(crossing);
    }

    @PutMapping("/{id}")
    public LevelCrossing updateCrossing(
            @PathVariable Long id,
            @RequestBody LevelCrossing updatedCrossing) {

        LevelCrossing crossing = crossingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crossing not found"));

        crossing.setCrossingCode(updatedCrossing.getCrossingCode());
        crossing.setLocation(updatedCrossing.getLocation());
        crossing.setCrossingStatus(updatedCrossing.getCrossingStatus());

        return crossingRepository.save(crossing);
    }

    @DeleteMapping("/{id}")
    public String deleteCrossing(@PathVariable Long id) {

        if (!crossingRepository.existsById(id)) {
            throw new RuntimeException("Crossing not found");
        }

        crossingRepository.deleteById(id);

        return "Crossing deleted successfully";
    }
}