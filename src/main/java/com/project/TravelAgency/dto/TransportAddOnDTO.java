package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.Transport;
import lombok.Data;

@Data
public class TransportAddOnDTO {
    private Long id;
    private String title;
    private String description;
    private Transport transport;
}
