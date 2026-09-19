package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.RailwayStation;
import com.railwaysystem.backend.repository.RailwayStationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@CrossOrigin(origins = "*")
public class RailwayStationController {

    private final RailwayStationRepository repository;

    public RailwayStationController(RailwayStationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RailwayStation> getAllStations() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public RailwayStation getStation(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));
    }

    @PostMapping
    public RailwayStation createStation(@RequestBody RailwayStation station) {
        return repository.save(station);
    }

    @PutMapping("/{id}")
    public RailwayStation updateStation(
            @PathVariable Long id,
            @RequestBody RailwayStation updatedStation) {

        RailwayStation station = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        station.setStationName(updatedStation.getStationName());
        station.setStationCode(updatedStation.getStationCode());
        station.setZone(updatedStation.getZone());

        return repository.save(station);
    }

    @DeleteMapping("/{id}")
    public String deleteStation(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Station not found");
        }

        repository.deleteById(id);

        return "Station deleted successfully";
    }
}