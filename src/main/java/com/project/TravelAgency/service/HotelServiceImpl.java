package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.repo.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HotelServiceImpl implements HotelService{

    @Autowired
    private HotelRepo hotelRepo;

    @Override
    public Hotel addHotel(Hotel hotel) {
        hotel.setRating(0.0);
        return hotelRepo.save(hotel);
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepo.deleteById(id);
    }

    @Override
    public Page<Hotel> findAllHotels(Pageable pageable) {
        return hotelRepo.findAll(pageable);
    }
}
