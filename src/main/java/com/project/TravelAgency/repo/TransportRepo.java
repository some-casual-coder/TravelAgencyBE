package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Image;
import com.project.TravelAgency.entity.Transport;
import com.project.TravelAgency.entity.TransportAddOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportRepo extends JpaRepository<Transport, Long> {

    //Find all and display as pages
    Page<Transport> findAll(Pageable pageable);

    //find by id


    //find by latlng
    @Query(value = "SELECT * from means_of_transport where latitude" +
            " BETWEEN :latStart AND :latEnd AND longitude BETWEEN :lngStart and :lngEnd",
            nativeQuery = true)
    List<Transport> findByLatLng(@Param("latStart") Double latStart,
                           @Param("latEnd") Double latEnd,
                           @Param("lngStart") Double lngStart,
                           @Param("lngEnd") Double lngEnd);

    //find by type
    @Query(value = "SELECT * from means_of_transport where transportType = :transportType", nativeQuery = true)
    List<Transport> findByTransportType(@Param("transportType") Long transportType);

    //find by capacity
    @Query(value = "SELECT * from means_of_transport where capacity >= :capacity order by capacity ASC", nativeQuery = true)
    List<Transport> findByCapacity(@Param("capacity") int capacity);

    //find all images
    @Query(value = "SELECT all_images.id, all_images.imageUrl from all_images " +
            "where transportId = :transportId order by transportId ASC", nativeQuery = true)
    List<Image> findAllTransportImages(@Param("transportId") Long transportId);

    //find all addons for transport means
    @Query(value = "select transport_addons.title, transport_addons.description from transport_addons " +
            "where transportId = :transportId", nativeQuery = true)
    List<TransportAddOn> findAllAddOns(@Param("transportId") Long transportId);

    //find all owned by
    List<Transport> findByOwner(Long owner);

    //find by price
    @Query(value = "SELECT * from means_of_transport where price <= :price order by price DESC", nativeQuery = true)
    List<Transport> findAllBelowPrice(@Param("price") double price);

}
