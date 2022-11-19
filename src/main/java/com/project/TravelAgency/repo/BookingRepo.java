package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Booking;
import com.project.TravelAgency.entity.Room;
import com.project.TravelAgency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findByUserOrderByIdDesc(User user);
    List<Booking> findByPaymentCompletedOrderByIdDesc(boolean paymentCompleted);
    List<Booking> findByCancelled(boolean cancelled);


    List<Booking> findByRoom(Room room);

    @Query(value = "SELECT bookings.* from bookings where (stay_completed) = true AND (payment_completed) = true inner join rooms ON " +
            "bookings.room_id = :roomId inner join hotels ON rooms.hotel_id = hotels.id where " +
            "hotels.user_id = :userId", nativeQuery = true)
    List<Booking> findBookingsRequiringConfirmation(Long roomId, Long userId);

    @Query(value = "SELECT bookings.* from bookings inner join rooms ON " +
            "bookings.room_id = :roomId inner join hotels ON rooms.hotel_id = hotels.id where " +
            "hotels.user_id = :userId order by id desc", nativeQuery = true)
    List<Booking> findAllBookingsForHotelOwnedByUser(Long roomId, Long userId);

}
