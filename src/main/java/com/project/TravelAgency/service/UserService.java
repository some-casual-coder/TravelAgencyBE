package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.Email;
import com.project.TravelAgency.entity.PasswordReset;
import com.project.TravelAgency.entity.User;
import com.project.TravelAgency.entity.VerificationCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;
import java.util.List;

public interface UserService {
    User registerUser(User user);
    boolean deleteUser(Long id);
    User findByEmail(String email);
    Page<User> findAll(Pageable pageable);
    VerificationCode getVerificationCode(String code);
    VerificationCode createVerificationCodeForUser(User user, String code);

    VerificationCode updateCode(VerificationCode verificationCode);

    VerificationCode generateNewVerificationCode(String code);
    PasswordReset createPasswordResetTokenForUser(String email, String resetToken);
    PasswordReset getPasswordResetToken(String token);
    String validateVerificationCode(String code);
    VerificationCode findVerificationCodeByUser(String email);
    boolean emailExists(String email);
    void sendVerificationEmail(String appURL, Email email, String verificationCode) throws MessagingException;
    void sendPwdResetEmail(String appURL, Email email, String resetToken) throws MessagingException;
    String validatePasswordResetToken(String token);
    boolean changeUserPassword(User user, String newPassword) throws PersistenceException;
    User getUserByPasswordResetToken(String token);
}
