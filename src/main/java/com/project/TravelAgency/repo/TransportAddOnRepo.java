package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.TransportAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportAddOnRepo extends JpaRepository<TransportAddOn, Long> {
}
