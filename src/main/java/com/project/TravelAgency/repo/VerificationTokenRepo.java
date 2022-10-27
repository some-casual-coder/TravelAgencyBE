package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.User;
import com.project.TravelAgency.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationCode, Long>  {
    VerificationCode findByVerificationCode(String verificationCode);
    VerificationCode findByUser(User user);
}
