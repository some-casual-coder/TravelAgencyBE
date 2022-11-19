package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TripOptionRepo optionRepo;

    @Autowired
    private UserPaymentRepo paymentRepo;

    @Autowired
    private RoomRepo roomRepo;

    @Override
    public Booking makeBooking(Booking booking) {
        Room room = roomRepo.findById(booking.getRoom().getId()).orElseThrow();
        Double price = room.getPricePerDay();
        Long start = booking.getFromDate().getTime();
        Long end = booking.getToDate().getTime();
        long timeDiff = Math.abs(start - end);
        Long days = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        Double totalCost = price * days;
        booking.setTotalCost(totalCost);
        return bookingRepo.save(booking);
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepo.deleteById(id);
    }

    @Override
    public TripOption addTripOption(TripOption tripOption) {

        Double price = tripOption.getTransport().getPrice();
        Long start = tripOption.getFromDate().getTime();
        Long end = tripOption.getToDate().getTime();
        long timeDiff = Math.abs(start - end);
        Long days = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        Double totalCost = price * days;
        tripOption.setTotalCost(totalCost);
        return optionRepo.save(tripOption);
    }

    @Override
    public TripOption findOptionById(Long id) {
        return optionRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Override
    public void removeTripOption(Long id) {
        optionRepo.deleteById(id);
    }

    @Override
    public UserPayment makePayment(UserPayment payment) {
        try {
            if (payment.getTripOption() == null) {
                Booking booking = findById(payment.getBooking().getId());
                Double currentPayments = booking.getTotalPaymentsMade();
                booking.setTotalPaymentsMade(currentPayments + payment.getAmount());
                if (booking.getTotalCost() <= booking.getTotalPaymentsMade()) {
                    booking.setPaymentCompleted(true);
                }
            } else if (payment.getBooking() == null) {
                TripOption tripOption = findOptionById(payment.getTripOption().getId());
                Double currentPayments = tripOption.getTotalPaymentsMade();
                tripOption.setTotalPaymentsMade(currentPayments + payment.getAmount());
                if (tripOption.getTotalCost() <= tripOption.getTotalPaymentsMade()) {
                    tripOption.setPaymentCompleted(true);
                }
            }
            return paymentRepo.save(payment);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public List<TripOption> findAllTripOptionsForBooking(Booking booking) {
        return optionRepo.findByBooking(booking);
    }

    @Override
    public List<TripOption> findBookingsForTransport(Transport transport) {
        return optionRepo.findByTransport(transport);
    }

    @Override
    public List<Booking> findAllBookingsForUser(User user) {
        return bookingRepo.findByUserOrderByIdDesc(user);
    }

    @Override
    public List<Booking> findBookingsForRoom(Room room) {
        return bookingRepo.findByRoom(room);
    }

    @Override
    public List<Booking> findBookingsRequiringConfirmation(Long roomId, Long userId) {
        return bookingRepo.findBookingsRequiringConfirmation(roomId, userId);
    }

    @Override
    public List<Booking> findAllBookingsForHotelsOwnedByUser(Long userId) {
        List<Room> ownedRooms = roomRepo.findByOwner(userId);
        List<Booking> allBookings = new ArrayList<>();
        for (Room ownedRoom : ownedRooms) {
            allBookings.addAll(bookingRepo.findByRoom(ownedRoom));
        }
        return allBookings;
    }

    @Override
    public List<Booking> findAllPaymentNotCompleted() {
        return bookingRepo.findByPaymentCompletedOrderByIdDesc(true);
    }

    @Override
    public List<Booking> findAllPaymentCompleted() {
        return bookingRepo.findByPaymentCompletedOrderByIdDesc(false);
    }

    @Override
    public List<Booking> findAllCancelled() {
        return bookingRepo.findByCancelled(true);
    }

    @Override
    public List<UserPayment> findAllPaymentsLatest() {
        return paymentRepo.findByOrderByDateMadeDesc();
    }

    @Override
    public List<UserPayment> findAllPaymentsForUser(User user) {
        return paymentRepo.findByUserOrderByDateMadeDesc(user);
    }

    @Override
    public List<UserPayment> findAllPaymentsByMethod(String paymentMethod) {
        return paymentRepo.findByPaymentMethodOrderByDateMadeDesc(paymentMethod);
    }
}
