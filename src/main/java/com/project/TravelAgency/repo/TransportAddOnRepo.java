package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.Transport;
import com.project.TravelAgency.entity.TransportAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportAddOnRepo extends JpaRepository<TransportAddOn, Long> {
    //find all addons for transport means
    List<TransportAddOn> findByTransport(Transport transport);
}
