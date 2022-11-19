package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.FavouriteVehicle;
import com.project.TravelAgency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteVehicleRepo extends JpaRepository<FavouriteVehicle, Long> {
    List<FavouriteVehicle> findByUser(User user);
}
