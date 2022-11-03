package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
    //find images for hotel
    @Query(value = "SELECT * from all_images where hotel_id = :hotelId", nativeQuery = true)
    List<Image> findAllHotelImages(@Param("hotelId") Long hotelId);

    //find all images for room
    @Query(value = "SELECT * from all_images where room_id = :roomId", nativeQuery = true)
    List<Image> findAllRoomImages(@Param("roomId") Long roomId);

    //find all transport means images
    @Query(value = "SELECT * from all_images " +
            "where transport_id = :transportId order by transport_id ASC", nativeQuery = true)
    List<Image> findAllTransportImages(@Param("transportId") Long transportId);
}
