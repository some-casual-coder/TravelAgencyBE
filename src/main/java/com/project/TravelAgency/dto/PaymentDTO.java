package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.Booking;
import com.project.TravelAgency.entity.TripOption;
import com.project.TravelAgency.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {
    private Long id;
    private User user;
    private Booking booking;
    private TripOption tripOption;
    private Double amount;
    private Date dateMade;
    private String paymentMethod;
}
