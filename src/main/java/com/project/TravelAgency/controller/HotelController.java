package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.AmenityDTO;
import com.project.TravelAgency.dto.HotelDTO;
import com.project.TravelAgency.dto.ImageDTO;
import com.project.TravelAgency.dto.UserDTO;
import com.project.TravelAgency.entity.Amenity;
import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.Image;
import com.project.TravelAgency.entity.User;
import com.project.TravelAgency.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

//Todo: wrap methods in try catch blocks with custom exceptions for error handling
@RestController
@CrossOrigin("/*")
@Slf4j
public class HotelController {

    @Autowired
    private static ModelMapper modelMapper;

    @Autowired
    private HotelService hotelService;

    //add hotel
    @PostMapping({"/hotel/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public HotelDTO addHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotel = convertToEntity(hotelDTO);
        return convertToDto(hotelService.addHotel(hotel));
    }

    //find hotel by id
    @GetMapping({"/hotel/get"})
    public HotelDTO findById(@RequestParam Long hotelId) {
        return convertToDto(hotelService.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(hotelId))));
    }

    //update hotel
    @PostMapping({"/hotel/update"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public HotelDTO updateHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotel = convertToEntity(hotelDTO);
        return convertToDto(hotelService.addHotel(hotel));
    }

    //delete hotel
    @DeleteMapping({"/hotel/delete"})
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void deleteHotel(@RequestParam Long hotelId) {
        hotelService.deleteHotel(hotelId);
    }


    //add hotel image
    @PostMapping({"/hotel/image/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ImageDTO addHotelImage(@RequestBody ImageDTO imageDTO) {
        Image image = convertToImageEntity(imageDTO);
        return convertToImageDto(hotelService.addHotelImage(image));
    }

    //delete hotel image
    @DeleteMapping({"/hotel/image/delete"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public void deleteHotelImage(@RequestParam Long imageId) {
        hotelService.deleteHotelImage(imageId);
    }

    //find all hotel images
    @GetMapping({"/hotel/image/all"})
    public List<Image> findAllHotelImages(@RequestParam Long hotelId) {
        return hotelService.findAllHotelImages(hotelId);
    }

    //add hotel amenity => Note: Amenity DTO has hotelID thus this should be set in the DTO on the frontend
    @PostMapping({"/hotel/amenity/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public AmenityDTO addAmenityToHotel(@RequestBody AmenityDTO amenityDTO) {
        Amenity amenity = convertToAmenityEntity(amenityDTO);
        return convertToAmenityDto(hotelService.addAmenity(amenity, amenityDTO.getHotelId()));
    }

    //todo: convert all amenities to dto before presenting them
    //find all hotel amenities
    @GetMapping({"/hotel/amenity/all"})
    public List<Amenity> findAllHotelAmenities(@RequestParam Long hotelId) {
        return hotelService.findAllHotelAmenities(hotelId);
    }

    //remove hotel amenity
    @DeleteMapping({"/hotel/amenity/remove"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public void deleteHotelAmenity(@RequestParam Long hotelId, @RequestParam Long amenityId) {
        hotelService.removeHotelAmenity(hotelId, amenityId);
    }

    //find all hotels
    @GetMapping({"/hotel/all"})
    public Page<Hotel> findAllHotels(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return hotelService.findAllHotels(pageable);
    }

    //todo: convert all hotels to dto before returning them for all endpoints below

    //find all by coordinates
    @GetMapping({"/hotel/all/nearby"})
    public List<Hotel> findByCoordinates(@RequestParam Double lat, @RequestParam Double lng){
        return hotelService.findByCoordinates(lat, lng);
    }

    //find all by town
    @GetMapping({"/hotel/all/town"})
    public List<Hotel> findByTown(@RequestParam String town){
        return hotelService.findByTown(town);
    }

    //find by name match
    @GetMapping({"/hotel/all/name"})
    public List<Hotel> findByName(@RequestParam String name){
        return hotelService.findByName(name);
    }

    //find all above rating
    @GetMapping({"/hotel/all/rating"})
    public List<Hotel> findAboveRating(@RequestParam double rating){
        return hotelService.findAllAboveRating(rating);
    }

    protected static HotelDTO convertToDto(Hotel hotel) {
        return modelMapper.map(hotel, HotelDTO.class);
    }

    protected static Hotel convertToEntity(HotelDTO hotelDTO) {
        return modelMapper.map(hotelDTO, Hotel.class);
    }

    protected static ImageDTO convertToImageDto(Image image) {
        return modelMapper.map(image, ImageDTO.class);
    }

    protected static Image convertToImageEntity(ImageDTO imageDTO) {
        return modelMapper.map(imageDTO, Image.class);
    }

    protected static AmenityDTO convertToAmenityDto(Amenity amenity) {
        return modelMapper.map(amenity, AmenityDTO.class);
    }

    protected static Amenity convertToAmenityEntity(AmenityDTO amenityDTO) {
        return modelMapper.map(amenityDTO, Amenity.class);
    }
}
