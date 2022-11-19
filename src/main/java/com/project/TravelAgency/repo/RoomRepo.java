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
    @Query(value = "DELETE from room_amenities where room_id = :roomId AND amenity_id = :amenityId", nativeQuery = true)
    void removeRoomAmenity(@Param("roomId") Long roomId, @Param("amenityId") Long amenityId);

    //find all as pages
    List<Room> findAll();

    //find by hotel id
    List<Room> findByHotel(Hotel hotel);

    @Query(value = "select rooms.* from rooms inner join hotels on rooms.hotel_id =hotels.id where hotels.user_id= :owner", nativeQuery = true)
    List<Room> findByOwner(@Param("owner") Long owner);
    
    //find by capacity and above
    List<Room> findByCapacityGreaterThanEqualOrderByCapacityAsc(int capacity);

    //find by price per day and below
    List<Room> findByPricePerDayLessThanEqualOrderByPricePerDayDesc(double pricePerDay);

}
