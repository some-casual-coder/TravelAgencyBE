package com.project.TravelAgency.controller;

import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.error.UserAlreadyExistsException;
import com.project.TravelAgency.service.JwtService;
import com.project.TravelAgency.service.UserService;
import com.project.TravelAgency.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin("/*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/registerUser"})
    public User registerUser(@RequestBody User user, HttpServletRequest request) throws MessagingException {
        try{
            String baseURL = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            User registeredUser = userService.registerUser(user);
            String code = UUID.randomUUID().toString();
            VerificationCode verificationCode = userService.createVerificationCodeForUser(registeredUser, code);
            Email email = new Email();
            email.setTo(registeredUser.getEmail());
            email.setModel(generateEmailModel(
                    registeredUser.getFirstName(),
                    registeredUser.getLastName()));
            userService.sendVerificationEmail(baseURL, email, verificationCode.getVerificationCode());
            return registeredUser;
        }catch (UserAlreadyExistsException ex){
            System.out.println(ex);
            throw new UserAlreadyExistsException(ex);
        } catch (MessagingException e) {
            throw new MessagingException(e.toString());
        }
    }

    @GetMapping("/user/verifyUser")
    public boolean verifyUser(@RequestParam String code){
        String result = userService.validateVerificationCode(code);
        if (result == UserServiceImpl.VALID){
            return true;
        }
        System.out.println(result);
        return false;
    }

    @GetMapping("/resendVerificationEmail")
    public void resendVerificationEmail(@RequestParam String email, HttpServletRequest request) throws MessagingException {
        String baseURL = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        VerificationCode verificationCode = userService.findVerificationCodeByUser(email);
        String code = UUID.randomUUID().toString();
        verificationCode.updateToken(code);
        userService.updateCode(verificationCode);
        Email mail = new Email();
        mail.setTo(verificationCode.getUser().getEmail());
        mail.setModel(generateEmailModel(
                verificationCode.getUser().getFirstName(),
                verificationCode.getUser().getLastName()));
        userService.sendVerificationEmail(baseURL, mail, verificationCode.getVerificationCode());
    }

    @PostMapping({"/user/resetPassword"})
    public boolean resetPassword(@RequestParam String email, HttpServletRequest request) throws MessagingException {
        try{
            String baseURL = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            String token = UUID.randomUUID().toString();
            PasswordReset passwordResetToken = userService.createPasswordResetTokenForUser(email, token);
            Email emailToSend = new Email();
            emailToSend.setTo(email);
            Map<String, Object> model = new HashMap<>();
            model.put("email", email);
            emailToSend.setModel(model);
            userService.sendPwdResetEmail(baseURL, emailToSend, passwordResetToken.getToken());
            return true;
        }catch (UserAlreadyExistsException ex){
            System.out.println(ex);
            throw new UserAlreadyExistsException(ex);
        }
    }

    @PostMapping("/user/changePassword")
    public boolean changePassword(@RequestParam String resetToken, @RequestParam String newPassword){
        String validatedToken = userService.validatePasswordResetToken(resetToken);
        if (validatedToken != null){
            System.out.println(validatedToken);
           return false;
        }
        User user = userService.getUserByPasswordResetToken(resetToken);
        if (user != null){
            userService.changeUserPassword(user, newPassword);
            return true;
        }
        return false;
    }

    @GetMapping("/checkEmailExists")
    public boolean checkEmailExists(@RequestParam("email") String email){
        return userService.emailExists(email);
    }


    @GetMapping({"/forUser"})
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public String forUser(){
        return "This is for user";
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String forAdmin(){
        return "This URL is only for admins";
    }

    @GetMapping({"/forHost"})
    @PreAuthorize("hasRole('ROLE_HOST')")
    public String forHost(){
        return "This URL is only for hosts";
    }

    @GetMapping({"/forSuperAdmin"})
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String forSuperAdmin(){
        return "This URL is only for super admin";
    }

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }

    private Map<String, Object> generateEmailModel(String fName, String lName){
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", fName);
        model.put("lastName", lName);
        return model;
    }

}
