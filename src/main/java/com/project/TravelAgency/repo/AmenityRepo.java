package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityRepo extends JpaRepository<Amenity, Long> {
    //find amenities for hotel
    @Query(value = "SELECT * from amenities " +
            "INNER JOIN hotel_amenities ON amenities.id = hotel_amenities.amenity_id " +
            " where hotel_amenities.hotel_id = :hotelId order by amenities.id ASC", nativeQuery = true)
    List<Amenity> findAllHotelAmenities(@Param("hotelId") Long hotelId);

    //find all amenities for room
    @Query(value = "SELECT * from amenities " +
            "INNER JOIN room_amenities ON amenities.id = room_amenities.amenity_id " +
            " where room_amenities.room_id = :roomId order by amenities.id ASC", nativeQuery = true)
    List<Amenity> findAllRoomAmenities(@Param("roomId") Long roomId);
}
