package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.Room;
import com.project.TravelAgency.entity.Transport;
import lombok.Data;

@Data
public class ImageDTO {
    private Long id;
    private String imageUrl;
    private Transport transport;
    private Hotel hotel; //hotel_id if it fails
    private Room room;
}
