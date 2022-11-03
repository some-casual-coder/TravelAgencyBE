package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    //remove an amenity from a room
    @Modifying
    @Query(value = "DELETE from room_amenities where roomId = :roomId AND amenityId = :amenityId", nativeQuery = true)
    void removeRoomAmenity(@Param("roomId") Long roomId, @Param("amenityId") Long amenityId);

    //find all as pages
    Page<Room> findAll(Pageable pageable);

    //find by hotel id
    List<Room> findByHotel(Long hotel);

    //find by capacity and above
    List<Room> findByCapacityGreaterThanEqualOrderByCapacityAsc(int capacity);

    //find by price per day and below
    List<Room> findByPricePerDayLessThanEqualOrderByPricePerDayDesc(double pricePerDay);

}
