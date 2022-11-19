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
public interface HotelRepo extends JpaRepository<Hotel, Long> {

    //delete hotel amenity
    @Modifying
    @Query(value = "DELETE from hotel_amenities where hotel_id = :hotelId AND amenity_id = :amenityId", nativeQuery = true)
    void removeHotelAmenity(@Param("hotelId") Long hotelId, @Param("amenityId") Long amenityId);

    Page<Hotel> findAll(Pageable pageable);

    List<Hotel> findByUser(User user);

    @Query(value = "SELECT * from hotels where (latitude" +
            " BETWEEN :latStart AND :latEnd) AND (longitude BETWEEN :lngStart and :lngEnd)",
            nativeQuery = true)
    List<Hotel> findByLatLng(@Param("latStart") Double latStart,
                                 @Param("latEnd") Double latEnd,
                                 @Param("lngStart") Double lngStart,
                                 @Param("lngEnd") Double lngEnd);

    //find by rating
    @Query(value = "SELECT * from hotels where rating >= :rating", nativeQuery = true)
    List<Hotel> findByRating(@Param("rating") double rating);

    //find by town
    List<Hotel> findByTownContainingIgnoreCase(String town);

    //find by name
    @Query(value = "SELECT * from hotels where lower(name) LIKE %:fullName% OR " +
            "name LIKE :first% OR " +
            "name LIKE %:middle% OR " +
            "name LIKE %:last", nativeQuery = true)
    List<Hotel> findByHotelName(@Param("fullName") String fullName,
                                @Param("first") String first,
                                @Param("last") String last,
                                @Param("middle") String middle
                                );

}
