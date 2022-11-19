package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.repo.ReviewRepo;

import java.util.List;

public interface CustomerService {

    CustomerMessage sendMessage(CustomerMessage customerMessage);

    List<CustomerMessage> findAllMessages();

    List<CustomerMessage> findAllMessagesNotHandled();

    List<CustomerMessage> findMessagesByIssueType(String issueType);

    void deleteMessage(Long id);

    FavouriteVehicle addFavouriteVehicle(FavouriteVehicle favouriteVehicle);

    List<FavouriteVehicle> myFavouriteVehicles(User user);

    void deleteFavouriteVehicle(Long id);

    HostRequest makeHostRequest(HostRequest hostRequest);

    HostRequest updateRequestStatus(HostRequest hostRequest);

    List<HostRequest> findAllHostRequests();

    List<HostRequest> findAllHostRequestsByStatus(int status);

    List<HostRequest> findAllHostRequestsForUser(User user);

    void deleteHostRequest(Long id);

    UserReview makeAReview(UserReview userReview);

    List<UserReview> findReviewsByHotel(Hotel hotel);

    List<UserReview> findReviewsByUser(User user);

    void deleteUserReview(Long id);

}
