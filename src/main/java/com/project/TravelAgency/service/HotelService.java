package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.Amenity;
import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.Image;
import com.project.TravelAgency.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    Hotel addHotel(Hotel hotel);

    //add amenity
    Amenity addAmenity(Amenity amenity, Long hotelId);

    //add image
    Image addHotelImage(Image image);

    //update hotel
    Hotel updateHotel(Hotel hotel);

    //update hotel amenity
    Amenity updateHotelAmenity(Amenity amenity);

    //delete hotel amenity
    void removeHotelAmenity(Long hotelId, Long amenityId);

    //delete hotel image
    void deleteHotelImage(Long imageId);

    void deleteHotel(Long id);

    //find by id
    Optional<Hotel> findById(Long id);

    Page<Hotel> findAllHotels(Pageable pageable);

    //find by rating
    List<Hotel> findAllAboveRating(double rating);

    //find by town
    List<Hotel> findByTown(String town);

    //find by coordinates
    List<Hotel> findByCoordinates(Double lat, Double lng);

    //find by name
    List<Hotel> findByName(String name);

    List<Hotel> findByUser(User user);

    //find images for hotel
    List<Image> findAllHotelImages(Long hotelId);

    //find amenities for hotel
    List<Amenity> findAllHotelAmenities(Long hotelId);
}
