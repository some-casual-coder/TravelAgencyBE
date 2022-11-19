package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.CustomerMessage;
import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.User;
import com.project.TravelAgency.entity.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<UserReview, Long> {
    List<UserReview> findAllByOrderByDateMadeDesc();
    List<UserReview> findByMadeBy(User madeBy);
    List<UserReview> findByHotel(Hotel hotel);
}
