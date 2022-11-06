package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Booking;
import com.project.TravelAgency.entity.Transport;
import com.project.TravelAgency.entity.TripOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripOptionRepo extends JpaRepository<TripOption, Long> {
    List<TripOption> findByBooking(Booking booking);
    List<TripOption> findByTransport(Transport transport);

}
