package com.project.TravelAgency.dto;

import lombok.Data;

@Data
public class HotelDTO {
    private Long id;
    private String name;
    private String town;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private double rating;
}
