package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.UserDTO;
import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.service.CustomerService;
import com.project.TravelAgency.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.project.TravelAgency.controller.UserController.convertToUserEntity;

@RestController
@CrossOrigin("/*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private HotelService hotelService;


    @PostMapping({"/user/sendMessage"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_USER')")
    public CustomerMessage sendMessage(@RequestBody CustomerMessage customerMessage){
        return customerService.sendMessage(customerMessage);
    }

    @GetMapping({"/admin/messages/all"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public List<CustomerMessage> findAllMessages(){
        return customerService.findAllMessages();
    }

    @GetMapping({"/admin/messages/all/notHandled"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public List<CustomerMessage> findAllMessagesNotHandled(){
        return customerService.findAllMessagesNotHandled();
    }

    @GetMapping({"/admin/messages/type"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public List<CustomerMessage> findMessagesByIssueType(@RequestParam String issueType){
        return customerService.findMessagesByIssueType(issueType);
    }

    @DeleteMapping({"/admin/messages/delete"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public void deleteMessage(@RequestParam("messageId") Long id){
        customerService.deleteMessage(id);
    }

    @PostMapping({"/user/favourites/addVehicle"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_USER')")
    public FavouriteVehicle addFavouriteVehicle(@RequestBody FavouriteVehicle favouriteVehicle){
        return customerService.addFavouriteVehicle(favouriteVehicle);
    }

    @GetMapping({"/user/favourites/all"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_USER')")
    public List<FavouriteVehicle> myFavouriteVehicles(@RequestBody UserDTO userDTO){
        User user = convertToUserEntity(userDTO);
        return customerService.myFavouriteVehicles(user);
    }

    @DeleteMapping({"/user/favourites/delete"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_USER')")
    public void deleteFavouriteVehicle(@RequestParam Long id){
        customerService.deleteFavouriteVehicle(id);
    }

    @PostMapping({"/user/hostRequest"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public HostRequest makeHostRequest(@RequestBody HostRequest hostRequest){
        return customerService.makeHostRequest(hostRequest);
    }

    @PutMapping({"/admin/hostRequest/update"})
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public HostRequest updateRequestStatus(@RequestBody HostRequest hostRequest){
        return customerService.updateRequestStatus(hostRequest);
    }

    @GetMapping({"/admin/hostRequest/all"})
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public List<HostRequest> findAllHostRequests(){
        return customerService.findAllHostRequests();
    }

    @GetMapping({"/admin/hostRequest/status"})
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public List<HostRequest> findAllHostRequestsByStatus(@RequestParam int status){
        return customerService.findAllHostRequestsByStatus(status);
    }

    @GetMapping({"hostRequest/user"})
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPER_ADMIN')")
    public List<HostRequest> findAllHostRequestsForUser(@RequestBody UserDTO userDTO){
        User user = convertToUserEntity(userDTO);
        return customerService.findAllHostRequestsForUser(user);
    }

    @DeleteMapping({"hostRequest/delete"})
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPER_ADMIN')")
    public void deleteHostRequest(@RequestParam Long id){
        customerService.deleteHostRequest(id);
    }

    @PostMapping({"/hotel/review/make"})
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserReview makeAReview(@RequestBody UserReview userReview){
        return customerService.makeAReview(userReview);
    }

    @GetMapping({"/hotel/reviews"})
    public List<UserReview> findReviewsByHotel(@RequestParam Long hotelId){
        Hotel hotel = hotelService.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(hotelId)));
        return customerService.findReviewsByHotel(hotel);
    }

    @GetMapping({"/user/reviews"})
    public List<UserReview> findReviewsByUser(@RequestBody UserDTO userDTO){
        User user = convertToUserEntity(userDTO);
        return customerService.findReviewsByUser(user);
    }

    @DeleteMapping({"review/delete"})
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPER_ADMIN')")
    public void deleteUserReview(@RequestParam("reviewId") Long id){
        customerService.deleteUserReview(id);
    }
}
