package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.FaultReport;
import com.railwaysystem.backend.entity.LevelCrossing;
import com.railwaysystem.backend.entity.RailwaySignal;
import com.railwaysystem.backend.entity.User;
import com.railwaysystem.backend.repository.FaultReportRepository;
import com.railwaysystem.backend.repository.LevelCrossingRepository;
import com.railwaysystem.backend.repository.RailwaySignalRepository;
import com.railwaysystem.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faults")
@CrossOrigin(origins = "*")
public class FaultReportController {

    private final FaultReportRepository faultRepository;
    private final LevelCrossingRepository crossingRepository;
    private final RailwaySignalRepository signalRepository;
    private final UserRepository userRepository;

    public FaultReportController(
            FaultReportRepository faultRepository,
            LevelCrossingRepository crossingRepository,
            RailwaySignalRepository signalRepository,
            UserRepository userRepository) {

        this.faultRepository = faultRepository;
        this.crossingRepository = crossingRepository;
        this.signalRepository = signalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<FaultReport> getAllFaults() {
        return faultRepository.findAll();
    }

    @GetMapping("/{id}")
    public FaultReport getFault(@PathVariable Long id) {
        return faultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fault not found"));
    }

    @GetMapping("/crossing/{crossingId}")
    public List<FaultReport> getFaultsByCrossing(
            @PathVariable Long crossingId) {
        return faultRepository.findByCrossingCrossingId(crossingId);
    }

    @GetMapping("/status/{status}")
    public List<FaultReport> getFaultsByStatus(
            @PathVariable String status) {
        return faultRepository.findByStatus(status);
    }

    @PostMapping("/crossing/{crossingId}/signal/{signalId}/user/{userId}")
    public FaultReport createFault(
            @PathVariable Long crossingId,
            @PathVariable Long signalId,
            @PathVariable Long userId,
            @RequestBody FaultReport fault) {

        LevelCrossing crossing = crossingRepository.findById(crossingId)
                .orElseThrow(() -> new RuntimeException("Crossing not found"));

        RailwaySignal signal = signalRepository.findById(signalId)
                .orElseThrow(() -> new RuntimeException("Signal not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        fault.setCrossing(crossing);
        fault.setSignal(signal);
        fault.setReportedBy(user);

        return faultRepository.save(fault);
    }

    @PutMapping("/{id}")
    public FaultReport updateFault(
            @PathVariable Long id,
            @RequestBody FaultReport updatedFault) {

        FaultReport fault = faultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fault not found"));

        fault.setFaultType(updatedFault.getFaultType());
        fault.setSeverity(updatedFault.getSeverity());
        fault.setStatus(updatedFault.getStatus());

        return faultRepository.save(fault);
    }

    @DeleteMapping("/{id}")
    public String deleteFault(@PathVariable Long id) {

        if (!faultRepository.existsById(id)) {
            throw new RuntimeException("Fault not found");
        }

        faultRepository.deleteById(id);

        return "Fault deleted successfully";
    }
}