package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.error.UserAlreadyExistsException;
import com.project.TravelAgency.repo.PasswordResetRepo;
import com.project.TravelAgency.repo.UserRepo;
import com.project.TravelAgency.repo.VerificationTokenRepo;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    public static final String INVALID = "Invalid";
    public static final String EXPIRED = "Expired";
    public static final String VALID = "Valid";

    public static final String EMAIL_TEMPLATE = "verification-email.flth";

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
        User registeredUser = userRepo.save(user);
        roleService.addRoleToUser(registeredUser.getEmail(), ERole.ROLE_USER);
        return userRepo.save(registeredUser);
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public VerificationCode getVerificationCode(String code) {
        return verificationTokenRepo.findByVerificationCode(code);
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
        VerificationCode verificationCode = verificationTokenRepo.findByVerificationCode(code);
        verificationCode.updateToken(UUID.randomUUID().toString());
        verificationTokenRepo.save(verificationCode);
        return verificationCode;
    }

    @Override
    public PasswordReset createPasswordResetTokenForUser(String email, String resetToken) {
        if(emailExists(email)){
            User user = findByEmail(email);
            System.err.println(user.getFirstName());
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
        VerificationCode verificationCode = verificationTokenRepo.findByVerificationCode(code);
        System.out.println(verificationCode.getVerificationCode());
        if(verificationCode == null){
            return INVALID;
        }
        User user = verificationCode.getUser();
        System.out.println(user.getEmail());
        Date expiration = verificationCode.getExpirationDate();
        Calendar currentCalendar = Calendar.getInstance();
        System.out.println(verificationCode.getExpirationDate());
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


    //TODO: get source email from application properties
    @Override
    @Async
    public void sendVerificationEmail(String appURL, Email email, String verificationCode) throws MessagingException {
        String link = "http://localhost:4200" + "/user/verifyUser?code=" + verificationCode;
        email.getModel().put("link", link);
//        email.setContent(getContentFromTemplate(email.getModel()));
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(email.getModel());
        //TODO get template path from properties or as a constant
        String htmlBody = templateEngine.process("verification_email.html", thymeleafContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email.getTo());
        //TODO get sender email from application properties
        helper.setFrom("anwendungstester@gmail.com");
        helper.setSubject("Verify Account");
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    @Override
    @Async
    public void sendPwdResetEmail(String appURL, Email email, String resetToken) throws MessagingException {
        String link = "http://localhost:4200" + "/user/changePassword?resetToken=" + resetToken;
        email.getModel().put("link", link);
//        email.setContent(getContentFromTemplate(email.getModel()));
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(email.getModel());
        //TODO get template path from properties or as a constant
        String htmlBody = templateEngine.process("reset_password.html", thymeleafContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email.getTo());
        //TODO get sender email from application properties
        helper.setFrom("anwendungstester@gmail.com");
        helper.setSubject("Reset Password");
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordReset passwordReset = passwordResetRepo.findByToken(token);
        return !isTokenFound(passwordReset) ? "invalidToken"
                : isTokenExpired(passwordReset) ? "expired"
                : null;
    }

    @Override
    public boolean changeUserPassword(User user, String newPassword) throws PersistenceException {
        try {
            user.setPassword(passwordEncoder.encode(newPassword));
            if (userRepo.save(user) != null){
                return true;
            }
            return false;
        }catch (PersistenceException ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        return passwordResetRepo.findByToken(token).getUser();
    }

    //Get string content from model with user details
    public String getContentFromTemplate(Map<String, Object> model){
        StringBuilder builder =new StringBuilder();
        try {
            builder.append(FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(EMAIL_TEMPLATE), model));
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    private boolean isTokenFound(PasswordReset passwordReset){
        return passwordReset != null;
    }

    private boolean isTokenExpired(PasswordReset passwordReset){
        Calendar calendar = Calendar.getInstance();
        return passwordReset.getExpirationDate().before(calendar.getTime());
    }
}
