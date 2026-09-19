package com.railwaysystem.backend.repository;

import com.railwaysystem.backend.entity.RailwaySignal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RailwaySignalRepository extends JpaRepository<RailwaySignal, Long> {

    List<RailwaySignal> findByStationStationId(Long stationId);
}