package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.HostRequest;
import com.project.TravelAgency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostRequestRepo extends JpaRepository<HostRequest, Long> {
    List<HostRequest> findAllByOrderByDateMadeDesc();
    List<HostRequest> findByUser(User user);
    List<HostRequest> findByStatus(int status);
}
