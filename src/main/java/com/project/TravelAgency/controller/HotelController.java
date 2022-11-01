package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.HotelDTO;
import com.project.TravelAgency.dto.UserDTO;
import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("/*")
@Slf4j
public class HotelController {

    @Autowired
    private ModelMapper modelMapper;

    //add hotel
    //find hotel by id
    //update hotel
    //delete hotel

    //add hotel image
    //delete hotel image
    //find all hotel images

    //add hotel amenity
    //find all hotel amenities
    //remove hotel amenity

    //find all hotels
    //find all by coordinates
    //find all by town
    //find by name match
    //find all above rating

    private HotelDTO convertToDto(Hotel hotel){
        return modelMapper.map(hotel, HotelDTO.class);
    }

    private Hotel convertToEntity(HotelDTO hotelDTO){
        return modelMapper.map(hotelDTO, Hotel.class);
    }
}
