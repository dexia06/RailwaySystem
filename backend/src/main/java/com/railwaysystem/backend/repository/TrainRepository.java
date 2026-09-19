package com.railwaysystem.backend.repository;

import com.railwaysystem.backend.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {

    List<Train> findByStationStationId(Long stationId);
}