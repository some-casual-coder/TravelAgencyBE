package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.Booking;
import com.project.TravelAgency.entity.Transport;
import lombok.Data;
import java.util.Date;

@Data
public class TripOptionDTO {
    private Long id;
    private Transport transport;
    private Booking booking;
    private Date fromDate;
    private Date toDate;
    private Double totalCost;
    private Double totalPaymentsMade;
    private boolean paymentCompleted;
}
