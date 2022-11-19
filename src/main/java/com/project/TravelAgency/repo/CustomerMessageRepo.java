package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.CustomerMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMessageRepo extends JpaRepository<CustomerMessage, Long> {

    List<CustomerMessage> findAllByOrderByDateMadeDesc();

//    issueType
    List<CustomerMessage> findByIssueType(String issueType);

//            message
    List<CustomerMessage> findByHandled(boolean handled);
}
