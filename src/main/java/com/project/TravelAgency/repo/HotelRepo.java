package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, Long> {
    Page<Hotel> findAll(Pageable pageable);
    //find all sort by name
    //find

    @Query(value = "SELECT * from hotels where latitude" +
            " BETWEEN :latStart AND :latEnd AND longitude BETWEEN :lngStart and :lngEnd",
            nativeQuery = true)
    List<Hotel> findByLatLng(@Param("latStart") Double latStart,
                                 @Param("latEnd") Double latEnd,
                                 @Param("lngStart") Double lngStart,
                                 @Param("lngEnd") Double lngEnd);

    //find by rating
    //find by town
    //find by name
    //find images for hotel
    //find amenities for hotel

}
