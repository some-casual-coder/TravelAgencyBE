package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.error.UserAlreadyExistsException;
import com.project.TravelAgency.repo.PasswordResetRepo;
import com.project.TravelAgency.repo.UserRepo;
import com.project.TravelAgency.repo.VerificationTokenRepo;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    public static final String INVALID = "Invalid";
    public static final String EXPIRED = "Expired";
    public static final String VALID = "Valid";

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${travelAgency.mail.verification}")
    private String verificationTemplate;

    @Value("${travelAgency.mail.passwordReset}")
    private String passwordResetTemplate;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    private PasswordResetRepo passwordResetRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public User registerUser(User user) {
        if (emailExists(user.getEmail())){
            throw new UserAlreadyExistsException("Email: " + user.getEmail() + " ,already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(false);
        user.setRoles(new HashSet<>());
        user.setHotels(new HashSet<>());
        User registeredUser = userRepo.save(user);
        roleService.addRoleToUser(registeredUser.getEmail(), ERole.ROLE_USER);
        return userRepo.save(registeredUser);
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    //TODO : find by email using like
    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public VerificationCode getVerificationCode(String code) {
        return verificationTokenRepo.findByToken(code);
    }

    @Override
    public VerificationCode createVerificationCodeForUser(User user, String code) {
        VerificationCode verificationCode = new VerificationCode(user, code);
        verificationTokenRepo.save(verificationCode);
        return verificationCode;
    }

    @Override
    public VerificationCode updateCode(VerificationCode verificationCode){
        return verificationTokenRepo.save(verificationCode);
    }

    @Override
    public VerificationCode generateNewVerificationCode(String code) {
        VerificationCode verificationCode = verificationTokenRepo.findByToken(code);
        verificationCode.updateToken(UUID.randomUUID().toString());
        verificationTokenRepo.save(verificationCode);
        return verificationCode;
    }

    @Override
    public PasswordReset createPasswordResetTokenForUser(String email, String resetToken) {
        if(emailExists(email)){
            User user = findByEmail(email);
            PasswordReset passwordReset = new PasswordReset(resetToken, user);
            return passwordResetRepo.save(passwordReset);
        }else{
            return null;
        }
    }

    @Override
    public PasswordReset getPasswordResetToken(String token) {
        return passwordResetRepo.findByToken(token);
    }

    @Override
    public String validateVerificationCode(String code) {
        VerificationCode verificationCode = verificationTokenRepo.findByToken(code);
        if(verificationCode == null){
            return INVALID;
        }
        User user = verificationCode.getUser();
        Date expiration = verificationCode.getExpirationDate();
        Calendar currentCalendar = Calendar.getInstance();
        log.info(String.valueOf(verificationCode.getExpirationDate()));
        if((expiration.getTime() - currentCalendar.getTime().getTime()) <= 0){
            verificationTokenRepo.delete(verificationCode);
            return EXPIRED;
        }
        user.setVerified(true);
        userRepo.save(user);
        return VALID;
    }

    @Override
    public VerificationCode findVerificationCodeByUser(String email) {
        User user = userRepo.findByEmail(email);
        return verificationTokenRepo.findByUser(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepo.existsUserByEmail(email);
    }


    @Override
    @Async
    public void sendVerificationEmail(String appURL, Email email, String verificationCode) throws MessagingException {
        String link = "http://localhost:4200" + "/user/verifyUser?code=" + verificationCode;
        email.getModel().put("link", link);
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(email.getModel());
        String htmlBody = templateEngine.process(verificationTemplate, thymeleafContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email.getTo());
        helper.setFrom(senderEmail);
        helper.setSubject("Verify Account");
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    @Override
    @Async
    public void sendPwdResetEmail(String appURL, Email email, String resetToken) throws MessagingException {
        String link = "http://localhost:4200" + "/user/changePassword?resetToken=" + resetToken;
        email.getModel().put("link", link);
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(email.getModel());
        String htmlBody = templateEngine.process(passwordResetTemplate, thymeleafContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email.getTo());
        helper.setFrom(senderEmail);
        helper.setSubject("Reset Password");
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordReset passwordReset = passwordResetRepo.findByToken(token);
        if (!isTokenFound(passwordReset)){
            return "invalidToken";
        }
        return isTokenExpired(passwordReset) ? "expired" : null;
    }

    @Override
    public boolean changeUserPassword(User user, String newPassword) throws PersistenceException {
        try {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
            return false;
        }catch (PersistenceException ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        return passwordResetRepo.findByToken(token).getUser();
    }

    private boolean isTokenFound(PasswordReset passwordReset){
        return passwordReset != null;
    }

    private boolean isTokenExpired(PasswordReset passwordReset){
        Calendar calendar = Calendar.getInstance();
        return passwordReset.getExpirationDate().before(calendar.getTime());
    }
}
