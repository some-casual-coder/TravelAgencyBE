package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.TripOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripOptionRepo extends JpaRepository<TripOption, Long> {
}
