package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepo extends JpaRepository<Amenity, Long> {
}
