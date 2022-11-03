package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.Amenity;
import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.Image;
import com.project.TravelAgency.repo.AmenityRepo;
import com.project.TravelAgency.repo.HotelRepo;
import com.project.TravelAgency.repo.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HotelServiceImpl implements HotelService{

    @Autowired
    private HotelRepo hotelRepo;

    @Autowired
    private AmenityRepo amenityRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Override
    public Hotel addHotel(Hotel hotel) {
        hotel.setRating(0.0);
        return hotelRepo.save(hotel);
    }

    @Override
    public Amenity addAmenity(Amenity amenity, Long hotelId) {
        Amenity savedAmenity = amenityRepo.save(amenity);
        hotelRepo.findById(hotelId).ifPresent(hotel -> hotel.getAmenities().add(savedAmenity));
        return savedAmenity;
    }

    @Override
    public Image addHotelImage(Image image) {
        image.setRoom(null);
        image.setTransport(null);
        return imageRepo.save(image);
    }

    @Modifying
    @Override
    public Hotel updateHotel(Hotel hotel) {
        return hotelRepo.save(hotel);
    }
    @Modifying
    @Override
    public Amenity updateHotelAmenity(Amenity amenity) {
        return amenityRepo.save(amenity);
    }

    @Override
    public void removeHotelAmenity(Long hotelId, Long amenityId) {
        hotelRepo.removeHotelAmenity(hotelId, amenityId);
    }

    @Override
    public void deleteHotelImage(Long imageId) {
        imageRepo.deleteById(imageId);
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepo.deleteById(id);
    }

    @Override
    public Optional<Hotel> findById(Long id) {
        return hotelRepo.findById(id);
    }

    @Override
    public Page<Hotel> findAllHotels(Pageable pageable) {
        return hotelRepo.findAll(pageable);
    }

    @Override
    public List<Hotel> findAllAboveRating(double rating) {
        return hotelRepo.findByRating(rating);
    }

    @Override
    public List<Hotel> findByTown(String town) {
        return hotelRepo.findByTownContainingIgnoreCase(town);
    }

    @Override
    public List<Hotel> findByCoordinates(Double lat, Double lng) {
//        double lngEnd = lng+=1;
//        System.out.println(lat + " , " + latEnd + " , " + lng + " , " + lngEnd);
        return hotelRepo.findByLatLng(lat, ++lat, lng, ++lng);
    }

    @Override
    public List<Hotel> findByName(String name) {
        int n = name.length();
        String first = String.valueOf(name.charAt(0));
        String last = String.valueOf(name.charAt(n-1));
        String middle = String.valueOf(name.charAt(2) + name.charAt(3));
        return hotelRepo.findByHotelName(name, first, last, middle);
    }

    @Override
    public List<Image> findAllHotelImages(Long hotelId) {
        return imageRepo.findAllHotelImages(hotelId);
    }

    @Override
    public List<Amenity> findAllHotelAmenities(Long hotelId) {
        return amenityRepo.findAllHotelAmenities(hotelId);
    }
}
