package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Transport;
import com.project.TravelAgency.entity.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportTypeRepo extends JpaRepository<TransportType, Long> {
}
