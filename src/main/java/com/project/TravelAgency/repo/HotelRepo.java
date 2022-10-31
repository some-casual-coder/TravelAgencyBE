package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, Long> {
    Page<Hotel> findAll(Pageable pageable);
}
