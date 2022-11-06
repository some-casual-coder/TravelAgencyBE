package com.project.TravelAgency.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_booking_room"))
    private Room room;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_booking_user"))
    private User user;

    private Date fromDate;
    private Date toDate;
    private Double totalCost;
    private boolean paymentCompleted;
    private boolean stayCompleted;
    private boolean stayCompletedConfirmation;
}
