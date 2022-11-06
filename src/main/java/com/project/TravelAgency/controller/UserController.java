package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.UserDTO;
import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.error.UserAlreadyExistsException;
import com.project.TravelAgency.service.JwtService;
import com.project.TravelAgency.service.UserService;
import com.project.TravelAgency.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping({"/registerUser"})
    public User registerUser(@RequestBody UserDTO userDTO, HttpServletRequest request) throws MessagingException {
        try{
            String baseURL = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            User user = convertToEntity(userDTO);
            User registeredUser = userService.registerUser(user);
            String code = UUID.randomUUID().toString();
            VerificationCode verificationCode = userService.createVerificationCodeForUser(registeredUser, code);
            Email email = new Email();
            email.setTo(registeredUser.getEmail());
            email.setModel(generateEmailModel(
                    registeredUser.getFirstName(),
                    registeredUser.getLastName()));
            userService.sendVerificationEmail(baseURL, email, verificationCode.getToken());
            return registeredUser;
        }catch (UserAlreadyExistsException ex){
            log.error(String.valueOf(ex));
            throw new UserAlreadyExistsException(ex);
        } catch (MessagingException e) {
            throw new MessagingException(e.toString());
        }
    }

    @GetMapping("/user/verifyUser")
    public boolean verifyUser(@RequestParam String code){
        String result = userService.validateVerificationCode(code);
        if (result.equals(UserServiceImpl.VALID)){
            return true;
        }
        log.error(result);
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
        userService.sendVerificationEmail(baseURL, mail, verificationCode.getToken());
    }

    @GetMapping({"/user/resetPassword"})
    public void resetPassword(@RequestParam String email, HttpServletRequest request) throws MessagingException {
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
        }catch (MessagingException ex){
            log.error(String.valueOf(ex));
            throw new MessagingException();
        }
    }

    @PostMapping("/user/changePassword")
    public boolean changePassword(@RequestParam String resetToken, @RequestParam String newPassword){
        String validatedToken = userService.validatePasswordResetToken(resetToken);
        if (validatedToken != null){
            log.error(validatedToken);
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

    @GetMapping("/users/findByEmail")
    public User findByEmail(@RequestParam String email){
        return userService.findByEmail(email);
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

    private UserDTO convertToDto(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

}
