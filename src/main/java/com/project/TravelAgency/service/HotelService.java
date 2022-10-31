package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {
    Hotel addHotel(Hotel hotel);

    void deleteHotel(Long id);

    Page<Hotel> findAllHotels(Pageable pageable);
}
