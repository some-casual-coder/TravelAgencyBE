package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.*;
import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.service.BookingService;
import com.project.TravelAgency.service.RoomService;
import com.project.TravelAgency.service.TransportService;
import com.project.TravelAgency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.project.TravelAgency.controller.RoomController.convertToRoomEntity;
import static com.project.TravelAgency.controller.TransportController.convertToTransportEntity;
import static com.project.TravelAgency.controller.UserController.convertToUserEntity;

@RestController
@CrossOrigin("/*")
public class BookingController {

    @Autowired
    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    protected static BookingDTO convertToBookingDTO(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    protected static Booking convertToBookingEntity(BookingDTO bookingDTO) {
        return modelMapper.map(bookingDTO, Booking.class);
    }

    protected static TripOptionDTO convertToOptionDTO(TripOption tripOption) {
        return modelMapper.map(tripOption, TripOptionDTO.class);
    }

    protected static TripOption convertToOptionEntity(TripOptionDTO tripOptionDTO) {
        return modelMapper.map(tripOptionDTO, TripOption.class);
    }

    protected static PaymentDTO convertToPaymentDTO(UserPayment userPayment) {
        return modelMapper.map(userPayment, PaymentDTO.class);
    }

    protected static UserPayment convertToPaymentEntity(PaymentDTO paymentDTO) {
        return modelMapper.map(paymentDTO, UserPayment.class);
    }

    //    Booking makeBooking(Booking booking);
    @PostMapping({"/booking/add"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public BookingDTO makeBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = convertToBookingEntity(bookingDTO);
        return convertToBookingDTO(bookingService.makeBooking(booking));
    }

    //    Booking findById(Long id);
    @GetMapping({"/booking/get"})
    public BookingDTO findById(@RequestParam Long bookingId) {
        return convertToBookingDTO(bookingService.findById(bookingId));
    }

    //    void deleteBooking(Long id);
    @DeleteMapping({"/booking/cancel"})
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_HOST')")
    public void deleteHotel(@RequestParam Long bookingId) {
        bookingService.deleteBooking(bookingId);
    }

    //    TripOption addTripOption(TripOption tripOption);
    @PostMapping({"/booking/option/add"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public TripOptionDTO addTripOption(@RequestBody TripOptionDTO optionDTO) {
        TripOption tripOption = convertToOptionEntity(optionDTO);
        return convertToOptionDTO(bookingService.addTripOption(tripOption));
    }

    //    TripOption findOptionById(Long id);
    @GetMapping({"/booking/option/get"})
    public TripOptionDTO findByOptionId(@RequestParam Long optionId) {
        return convertToOptionDTO(bookingService.findOptionById(optionId));
    }

    //    void removeTripOption(Long id);
    @DeleteMapping({"/booking/option/remove"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public void removeOption(@RequestParam Long optionId) {
        bookingService.removeTripOption(optionId);
    }

    //    UserPayment makePayment(UserPayment payment);
    @PostMapping({"/booking/makePayment"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public PaymentDTO makePayment(@RequestBody PaymentDTO paymentDTO) {
        UserPayment payment = convertToPaymentEntity(paymentDTO);
        return convertToPaymentDTO(bookingService.makePayment(payment));
    }

//    List<UserPayment> findAllPayments();

    //    List<Booking> findAllBookings();
    @GetMapping({"/booking/all"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public List<Booking> findAllBookings() {
        return bookingService.findAllBookings();
    }

    //    List<TripOption> findAllTripOptionsForBooking(Booking booking);
    @GetMapping({"/booking/option/all"})
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public List<TripOption> findAllTripOptionsForBooking(@RequestParam Long bookingId) {
        Booking booking = bookingService.findById(bookingId);
        return bookingService.findAllTripOptionsForBooking(booking);
    }

    //    List<TripOption> findBookingsForTransport(Transport transport);
    @GetMapping({"/booking/transport/all"})
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public List<TripOption> findBookingsForTransport(@RequestBody Long transportId) {
        Transport transport = transportService.findById(transportId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(transportId)));
        return bookingService.findBookingsForTransport(transport);
    }

    //    List<Booking> findAllBookingsForUser(User user);
    @GetMapping({"/booking/user/all"})
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public List<Booking> findAllBookingsForUser(@RequestBody Long userId) {
        User user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(userId)));
        return bookingService.findAllBookingsForUser(user);
    }

    //    List<Booking> findBookingsForRoom(Room room);
    @GetMapping({"/booking/room/all"})
    public List<Booking> findBookingsForRoom(@RequestBody Long roomId) {
        Room room = roomService.findById(roomId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(roomId)));
        return bookingService.findBookingsForRoom(room);
    }

    //    List<Booking> findBookingsRequiringConfirmation(Long roomId);
    @GetMapping({"/booking/room/confirmation"})
    @PreAuthorize("hasAnyRole('ROLE_HOST', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public List<Booking> findBookingsRequiringConfirmation(@RequestParam Long roomId, @RequestParam Long userId) {
        return bookingService.findBookingsRequiringConfirmation(roomId, userId);
    }

    //    List<UserPayment> findAllPaymentsLatest();
    @GetMapping({"/payments/all"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public List<UserPayment> findAllPayments() {
        return bookingService.findAllPaymentsLatest();
    }

    //    List<UserPayment> findAllPaymentsForUser(User user);
    @GetMapping({"/payments/user/all"})
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public List<UserPayment> findAllPaymentsForUser(@RequestParam Long userId) {
        User user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(userId)));
        return bookingService.findAllPaymentsForUser(user);
    }

    //    List<UserPayment> findAllPaymentsByMethod(String paymentMethod);
    @GetMapping({"/payments/method"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public List<UserPayment> findAllPaymentsByMethod(String method) {
        return bookingService.findAllPaymentsByMethod(method);
    }
}
