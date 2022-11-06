package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.User;
import com.project.TravelAgency.entity.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPaymentRepo extends JpaRepository<UserPayment, Long> {

    List<UserPayment> findByOrderByDateMadeDesc();
    List<UserPayment> findByPaymentMethodOrderByDateMadeDesc(String paymentMethod);
    List<UserPayment> findByUserOrderByDateMadeDesc(User user);

}
