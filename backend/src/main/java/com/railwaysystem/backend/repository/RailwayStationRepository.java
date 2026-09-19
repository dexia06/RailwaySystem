package com.railwaysystem.backend.repository;

import com.railwaysystem.backend.entity.RailwayStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RailwayStationRepository extends JpaRepository<RailwayStation, Long> {
}