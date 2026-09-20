package com.railwaysystem.backend.repository;

import com.railwaysystem.backend.entity.LevelCrossing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelCrossingRepository extends JpaRepository<LevelCrossing, Long> {

    List<LevelCrossing> findByStationStationId(Long stationId);
}