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
    @Query(value = "DELETE from room_amenities where roomId = :roomId AND amenityId = :amenityId")
    void removeRoomAmenity(@Param("roomId") Long roomId, @Param("amenityId") Long amenityId);

    //find all as pages
    Page<Room> findAll(Pageable pageable);

    //find by hotel id
    List<Room> findByHotel(Long hotel);

    //find by capacity and above
    List<Room> findByCapacityGreaterThanEqualOrderByCapacityAsc(int capacity);

    //find by price per day and below
    List<Room> findByPricePerDayLessThanEqualOrderByPricePerDayDesc(double pricePerDay);

    //find all images for room
    @Query(value = "SELECT id, imageUrl from all_images where roomId = :roomId", nativeQuery = true)
    List<Image> findAllRoomImages(@Param("roomId") Long roomId);

    //find all amenities for room
    @Query(value = "SELECT amenities.title, amenities.content from amenities " +
            "INNER JOIN room_amenities ON amenities.id = room_amenities.amenityId " +
            " where room_amenities.roomId = :roomId order by amenities.id ASC", nativeQuery = true)
    List<Amenity> findAllRoomAmenities(@Param("roomId") Long roomId);

}
