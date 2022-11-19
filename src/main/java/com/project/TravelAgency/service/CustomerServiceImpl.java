package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.repo.CustomerMessageRepo;
import com.project.TravelAgency.repo.FavouriteVehicleRepo;
import com.project.TravelAgency.repo.HostRequestRepo;
import com.project.TravelAgency.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerMessageRepo messageRepo;

    @Autowired
    private FavouriteVehicleRepo vehicleRepo;

    @Autowired
    private HostRequestRepo requestRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Override
    public CustomerMessage sendMessage(CustomerMessage customerMessage) {
        customerMessage.setDateMade(new Date());
        return messageRepo.save(customerMessage);
    }

    @Override
    public List<CustomerMessage> findAllMessages() {
        return messageRepo.findAllByOrderByDateMadeDesc();
    }

    @Override
    public List<CustomerMessage> findAllMessagesNotHandled() {
        return messageRepo.findByHandled(false);
    }

    @Override
    public List<CustomerMessage> findMessagesByIssueType(String issueType) {
        return messageRepo.findByIssueType(issueType);
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepo.deleteById(id);
    }

    @Override
    public FavouriteVehicle addFavouriteVehicle(FavouriteVehicle favouriteVehicle) {
        return vehicleRepo.save(favouriteVehicle);
    }

    @Override
    public List<FavouriteVehicle> myFavouriteVehicles(User user) {
        return vehicleRepo.findByUser(user);
    }

    @Override
    public void deleteFavouriteVehicle(Long id) {
        vehicleRepo.deleteById(id);
    }

    @Override
    public HostRequest makeHostRequest(HostRequest hostRequest) {
        hostRequest.setDateMade(new Date());
        return requestRepo.save(hostRequest);
    }

    @Override
    public HostRequest updateRequestStatus(HostRequest hostRequest){
        return requestRepo.save(hostRequest);
    }

    @Override
    public List<HostRequest> findAllHostRequests() {
        return requestRepo.findAllByOrderByDateMadeDesc();
    }

    @Override
    public List<HostRequest> findAllHostRequestsByStatus(int status) {
        return requestRepo.findByStatus(status);
    }

    @Override
    public List<HostRequest> findAllHostRequestsForUser(User user) {
        return requestRepo.findByUser(user);
    }

    @Override
    public void deleteHostRequest(Long id) {
        requestRepo.deleteById(id);
    }

    @Override
    public UserReview makeAReview(UserReview userReview) {
        userReview.setDateMade(new Date());
        return reviewRepo.save(userReview);
    }

    @Override
    public List<UserReview> findReviewsByHotel(Hotel hotel) {
        return reviewRepo.findByHotel(hotel);
    }

    @Override
    public List<UserReview> findReviewsByUser(User user) {
        return reviewRepo.findByMadeBy(user);
    }

    @Override
    public void deleteUserReview(Long id) {
        reviewRepo.deleteById(id);
    }
}
