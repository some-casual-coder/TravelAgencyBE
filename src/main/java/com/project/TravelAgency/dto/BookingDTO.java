package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.Room;
import com.project.TravelAgency.entity.User;
import lombok.Data;
import java.util.Date;

@Data
public class BookingDTO {
    private Long id;
    private Room room;
    private User user;
    private Date fromDate;
    private Date toDate;
    private Double totalCost;
    private Double totalPaymentsMade;
    private boolean paymentCompleted;
    private boolean stayCompleted;
    private boolean stayCompletedConfirmation;
}
