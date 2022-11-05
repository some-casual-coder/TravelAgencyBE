package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.Amenity;
import com.project.TravelAgency.entity.Hotel;
import lombok.Data;

import java.util.Set;

@Data
public class RoomDTO {
    private Long id;
    private String name;
    private int capacity;
    private double pricePerDay;
    private Hotel hotel;
    private Set<Amenity> amenities;
}
