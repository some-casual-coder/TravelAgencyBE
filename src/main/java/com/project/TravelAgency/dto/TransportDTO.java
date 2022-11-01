package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.TransportType;
import lombok.Data;

@Data
public class TransportDTO {
    private Long id;
    private String model;
    private String chargedPer;
    private int capacity;
    private double price;
    private Double latitude;
    private Double longitude;
    private TransportType transportType;
    private Long owner;
}
