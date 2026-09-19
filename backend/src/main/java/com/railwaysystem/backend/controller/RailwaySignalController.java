package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.RailwaySignal;
import com.railwaysystem.backend.entity.RailwayStation;
import com.railwaysystem.backend.repository.RailwaySignalRepository;
import com.railwaysystem.backend.repository.RailwayStationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signals")
@CrossOrigin(origins = "*")
public class RailwaySignalController {

    private final RailwaySignalRepository signalRepository;
    private final RailwayStationRepository stationRepository;

    public RailwaySignalController(
            RailwaySignalRepository signalRepository,
            RailwayStationRepository stationRepository) {
        this.signalRepository = signalRepository;
        this.stationRepository = stationRepository;
    }

    @GetMapping
    public List<RailwaySignal> getAllSignals() {
        return signalRepository.findAll();
    }

    @GetMapping("/{id}")
    public RailwaySignal getSignal(@PathVariable Long id) {
        return signalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signal not found"));
    }

    @GetMapping("/station/{stationId}")
    public List<RailwaySignal> getSignalsByStation(
            @PathVariable Long stationId) {
        return signalRepository.findByStationStationId(stationId);
    }

    @PostMapping("/station/{stationId}")
    public RailwaySignal createSignal(
            @PathVariable Long stationId,
            @RequestBody RailwaySignal signal) {

        RailwayStation station = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        signal.setStation(station);

        return signalRepository.save(signal);
    }

    @PutMapping("/{id}")
    public RailwaySignal updateSignal(
            @PathVariable Long id,
            @RequestBody RailwaySignal updatedSignal) {

        RailwaySignal signal = signalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signal not found"));

        signal.setSignalCode(updatedSignal.getSignalCode());
        signal.setSignalType(updatedSignal.getSignalType());
        signal.setCurrentStatus(updatedSignal.getCurrentStatus());

        return signalRepository.save(signal);
    }

    @DeleteMapping("/{id}")
    public String deleteSignal(@PathVariable Long id) {

        if (!signalRepository.existsById(id)) {
            throw new RuntimeException("Signal not found");
        }

        signalRepository.deleteById(id);

        return "Signal deleted successfully";
    }
}