package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepo extends JpaRepository<UserPayment, Long> {
}
