package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.TransportType;
import com.project.TravelAgency.entity.User;
import lombok.Data;

@Data
public class TransportDTO {
    private Long id;
    private String model;
    private int capacity;
    private double price;
    private String town;
    private TransportType transportType;
    private User owner;
}
