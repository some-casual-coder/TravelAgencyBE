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
    List<Booking> findByUser(User user);
    List<Booking> findByRoom(Room room);

    @Query(value = "SELECT * from bookings where (stay_completed = true) AND (paymentCompleted = true) where room_id = :roomId", nativeQuery = true)
    List<Booking> findBookingsRequiringConfirmation(Long roomId);

}
