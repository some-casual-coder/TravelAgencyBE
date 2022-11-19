package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.Amenity;
import com.project.TravelAgency.entity.Image;
import com.project.TravelAgency.entity.Room;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    //add room
    Room addRoom(Room room);

    //update room
    Room updateRoom(Room room);

    //find room by id
    Optional<Room> findById(Long id);

    //delete room
    void deleteRoom(Long roomId);

    //add amenity
    Amenity addAmenity(Amenity amenity, Long roomId);

    //update amenity
    Amenity updateAmenity(Amenity amenity);

    //remove amenity from room
    void removeAmenityFromRoom(Long roomId, Long amenityId);

    //add image
    Image addImage(Image image);

    //delete image
    void deleteImage(Long imageId);

    //find all rooms in hotel
    List<Room> findAllHotelRooms(Long hotelId);

    //find by capacity above
    List<Room> findAllAboveCapacity(int capacity);

    //find by price per day below
    List<Room> findAllBelowPrice(double price);

    //find all room images
    List<Image> findAllRoomImages(Long roomId);

    String findFirstRoomImage(Long roomId);

    //find all room amenities
    List<Amenity> findAllRoomAmenities(Long roomId);
}
