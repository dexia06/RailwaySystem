package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.RailwayStation;
import com.railwaysystem.backend.entity.Train;
import com.railwaysystem.backend.repository.RailwayStationRepository;
import com.railwaysystem.backend.repository.TrainRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
@CrossOrigin(origins = "*")
public class TrainController {

    private final TrainRepository trainRepository;
    private final RailwayStationRepository stationRepository;

    public TrainController(TrainRepository trainRepository,
                           RailwayStationRepository stationRepository) {
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
    }

    @GetMapping
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @GetMapping("/{id}")
    public Train getTrain(@PathVariable Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));
    }

    @GetMapping("/station/{stationId}")
    public List<Train> getTrainsByStation(@PathVariable Long stationId) {
        return trainRepository.findByStationStationId(stationId);
    }

    @PostMapping("/station/{stationId}")
    public Train createTrain(
            @PathVariable Long stationId,
            @RequestBody Train train) {

        RailwayStation station = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        train.setStation(station);

        return trainRepository.save(train);
    }

    @PutMapping("/{id}")
    public Train updateTrain(
            @PathVariable Long id,
            @RequestBody Train updatedTrain) {

        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        train.setTrainNumber(updatedTrain.getTrainNumber());
        train.setTrainName(updatedTrain.getTrainName());
        train.setCurrentStatus(updatedTrain.getCurrentStatus());

        return trainRepository.save(train);
    }

    @DeleteMapping("/{id}")
    public String deleteTrain(@PathVariable Long id) {

        if (!trainRepository.existsById(id)) {
            throw new RuntimeException("Train not found");
        }

        trainRepository.deleteById(id);

        return "Train deleted successfully";
    }
}