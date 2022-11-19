package com.project.TravelAgency.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_payments")
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_payment_user"))
    private User user;

    @OneToOne(targetEntity = Booking.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_payment_booking"))
    @ToString.Exclude
    private Booking booking;

    @OneToOne(targetEntity = TripOption.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_payment_trip"))
    @ToString.Exclude
    private TripOption tripOption;

    private Double amount;
    private Date dateMade;
    private String paymentMethod;
}
