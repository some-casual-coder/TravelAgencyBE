package com.project.TravelAgency.controller;
import com.project.TravelAgency.dto.HotelDTO;
import com.project.TravelAgency.dto.UserDTO;
import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.User;
import com.project.TravelAgency.service.HotelService;
import com.project.TravelAgency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/*")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/users/all")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public Page<User> findAllUsers(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("email").ascending());
        return userService.findAll(pageable);
    }

//    @PostMapping("/hotel/add")
//    @PreAuthorize("hasAnyRole('ROLE_HOST', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
//    public Hotel addHotel(@RequestBody HotelDTO hotelDTO){
//        return hotelService.addHotel(convertToEntity(hotelDTO));
//    }
//
//    @GetMapping("/hotels/all")
//    public Page<Hotel> findAllHotels(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
//        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
//        return hotelService.findAllHotels(pageable);
//    }

    private Hotel convertToEntity(HotelDTO hotelDTO){
        return modelMapper.map(hotelDTO, Hotel.class);
    }
}
