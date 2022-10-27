package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.PasswordReset;
import com.project.TravelAgency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepo extends JpaRepository<PasswordReset, Long> {
    PasswordReset findByToken(String token);
    PasswordReset findByUser(User user);

    //TODO: delete all tokens that have already expired
}
