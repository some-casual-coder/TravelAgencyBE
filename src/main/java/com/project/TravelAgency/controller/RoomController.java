package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.*;
import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static com.project.TravelAgency.controller.HotelController.*;

@RestController
@CrossOrigin("/*")
@Slf4j
public class RoomController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomService roomService;

    //add room
    @PostMapping({"/room/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public RoomDTO addRoom(@RequestBody RoomDTO roomDTO) {
        Room room = convertToEntity(roomDTO);
        return convertToDto(roomService.addRoom(room));
    }

    //find by id
    @GetMapping({"/room/get"})
    public RoomDTO findById(@RequestParam Long roomId) {
        return convertToDto(roomService.findById(roomId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(roomId))));
    }

    //update room
    @PutMapping({"/room/update"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public RoomDTO updateRoom(@RequestBody RoomDTO roomDTO) {
        Room room = convertToEntity(roomDTO);
        return convertToDto(roomService.addRoom(room));
    }

    //delete room
    @DeleteMapping({"/room/delete"})
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void deleteRoom(@RequestParam Long roomId) {
        roomService.deleteRoom(roomId);
    }

    //add image
    @PostMapping({"/room/image/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ImageDTO addHotelImage(@RequestBody ImageDTO imageDTO) {
        Image image = convertToImageEntity(imageDTO);
        return convertToImageDto(roomService.addImage(image));
    }

    //todo: make all endpoints that return a list return a page for improved performance

    //find all room images
    @GetMapping({"/room/image/all"})
    public List<Image> findAllRoomImages(@RequestParam Long roomId) {
        return roomService.findAllRoomImages(roomId);
    }

    //delete image
    @DeleteMapping({"/room/image/delete"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public void deleteRoomImage(@RequestParam Long imageId) {
        roomService.deleteImage(imageId);
    }

    //add amenity => Note: Amenity DTO has roomId thus this should be set in the DTO on the frontend
    @PostMapping({"/room/amenity/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public AmenityDTO addAmenityToRoom(@RequestBody AmenityDTO amenityDTO) {
        Amenity amenity = convertToAmenityEntity(amenityDTO);
        return convertToAmenityDto(roomService.addAmenity(amenity, amenityDTO.getRoomId()));
    }

    //remove amenity from room
    @DeleteMapping({"/room/amenity/remove"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public void deleteRoomAmenity(@RequestParam Long roomId, @RequestParam Long amenityId) {
        roomService.removeAmenityFromRoom(roomId, amenityId);
    }

    //find all room amenities
    @GetMapping({"/room/amenity/all"})
    public List<Amenity> findAllRoomAmenities(@RequestParam Long roomId) {
        return roomService.findAllRoomAmenities(roomId);
    }

    //find all hotel rooms
    @GetMapping({"/hotel/room/all"})
    public List<Room> findAllHotelRooms(@RequestParam Long hotelId){
        return roomService.findAllHotelRooms(hotelId);
    }

    //find all rooms above capacity
    @GetMapping({"/room/all/capacity"})
    public List<Room> findRoomsAboveCapacity(@RequestParam int capacity){
        return roomService.findAllAboveCapacity(capacity);
    }

    //find all rooms below price
    @GetMapping({"/room/all/price"})
    public List<Room> findAllRoomsBelowPrice(@RequestParam double price){
        return roomService.findAllBelowPrice(price);
    }

    private RoomDTO convertToDto(Room room){
        return modelMapper.map(room, RoomDTO.class);
    }

    private Room convertToEntity(RoomDTO roomDTO){
        return modelMapper.map(roomDTO, Room.class);
    }
}
