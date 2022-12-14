package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.*;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking makeBooking(Booking booking);

    Booking findById(Long id);

    void deleteBooking(Long id);

    TripOption addTripOption(TripOption tripOption);

    TripOption findOptionById(Long id);

    void removeTripOption(Long id);

    UserPayment makePayment(UserPayment payment);

    List<Booking> findAllBookings();

    List<TripOption> findAllTripOptionsForBooking(Booking booking);

    List<TripOption> findBookingsForTransport(Transport transport);

    List<Booking> findAllBookingsForUser(User user);

    List<Booking> findBookingsForRoom(Room room);

    List<Booking> findBookingsRequiringConfirmation(Long roomId, Long userId);
    List<Booking> findAllBookingsForHotelsOwnedByUser(Long userId);

    List<Booking> findAllPaymentNotCompleted();

    List<Booking> findAllPaymentCompleted();
    List<Booking> findAllCancelled();

    List<UserPayment> findAllPaymentsLatest();

    List<UserPayment> findAllPaymentsForUser(User user);

    List<UserPayment> findAllPaymentsByMethod(String paymentMethod);
}
