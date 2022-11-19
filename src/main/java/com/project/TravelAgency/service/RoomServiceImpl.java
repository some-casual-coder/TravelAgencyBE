package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.Amenity;
import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.Image;
import com.project.TravelAgency.entity.Room;
import com.project.TravelAgency.repo.AmenityRepo;
import com.project.TravelAgency.repo.HotelRepo;
import com.project.TravelAgency.repo.ImageRepo;
import com.project.TravelAgency.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private AmenityRepo amenityRepo;

    @Autowired
    private HotelRepo hotelRepo;

    @Override
    public Room addRoom(Room room) {
        room.setAmenities(new HashSet<>());
        return roomRepo.save(room);
    }

    @Override
    public Room updateRoom(Room room) {
        return roomRepo.save(room);
    }

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepo.findById(id);
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomRepo.deleteById(roomId);
    }

    @Override
    public Amenity addAmenity(Amenity amenity, Long roomId) {
        Amenity savedAmenity = amenityRepo.save(amenity);
        roomRepo.findById(roomId).ifPresent(room -> room.getAmenities().add(savedAmenity));
        return savedAmenity;
    }

    @Override
    public Amenity updateAmenity(Amenity amenity) {
        return amenityRepo.save(amenity);
    }

    @Override
    public void removeAmenityFromRoom(Long roomId, Long amenityId) {
        roomRepo.removeRoomAmenity(roomId, amenityId);
    }

    @Override
    public Image addImage(Image image) {
        return imageRepo.save(image);
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepo.deleteById(imageId);
    }

    @Override
    public List<Room> findAllHotelRooms(Long hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(hotelId)));
        return roomRepo.findByHotel(hotel);
    }

    @Override
    public List<Room> findAllAboveCapacity(int capacity) {
        return roomRepo.findByCapacityGreaterThanEqualOrderByCapacityAsc(capacity);
    }

    @Override
    public List<Room> findAllBelowPrice(double price) {
        return roomRepo.findByPricePerDayLessThanEqualOrderByPricePerDayDesc(price);
    }

    @Override
    public List<Image> findAllRoomImages(Long roomId) {
        return imageRepo.findAllRoomImages(roomId);
    }

    @Override
    public String findFirstRoomImage(Long roomId) {
        return imageRepo.findFirstRoomImage(roomId);
    }

    @Override
    public List<Amenity> findAllRoomAmenities(Long roomId) {
        return amenityRepo.findAllRoomAmenities(roomId);
    }
}
