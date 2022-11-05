package com.project.TravelAgency.dto;

import lombok.Data;

@Data
public class AmenityDTO {
    private Long id;
    private String title;
    private String content;
    private Long hotelId;
    private Long roomId;
}
