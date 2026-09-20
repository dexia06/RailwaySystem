package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.dto.SafetyResponse;
import com.railwaysystem.backend.service.SafetyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/safety")
@CrossOrigin(origins = "*")
public class SafetyController {

    private final SafetyService safetyService;

    public SafetyController(SafetyService safetyService) {
        this.safetyService = safetyService;
    }

    @PostMapping("/detect/{crossingId}/{distance}")
    public SafetyResponse detectTrain(
            @PathVariable Long crossingId,
            @PathVariable int distance) {

        return safetyService.detectTrain(crossingId, distance);
    }
}