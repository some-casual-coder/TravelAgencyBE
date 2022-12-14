package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TransportService {

    //add transport means
    Transport addMeansOfTransport(Transport transport);

    //update transport means
    Transport updateMeansOfTransport(Transport transport);

    //delete transport means by id
    void deleteById(Long id);

    //add addons
    TransportAddOn addTransportAddOn(TransportAddOn transportAddOn);

    //update addon
    TransportAddOn updateAddOn(TransportAddOn transportAddOn);

    //delete addon
    void deleteAddOn(Long id);

    TransportType addTransportType(TransportType transportType);

    //delete image
    void deleteImage(Long id);

    //add image
    Image addTransportImage(Image image);

    //Find all by pages
    Page<Transport> findAll(Pageable pageable);

    List<TransportType> findAllTypes();
    
    //find by id
    Optional<Transport> findById(Long id);

    //find by lat lng
    List<Transport> findByTown(String town);

    List<Transport> findByModel(String model);

    //find by type
    List<Transport> findByTransportType(Long transportType);

    //find by capacity
    List<Transport> findByCapacity(int capacity);

    //find all images
    List<Image> findAllTransportImages(Long transportId);

    //find all addons for transport means
    List<TransportAddOn> findAllTransportAddOns(Transport transport);

    //find all owned by
    List<Transport> findByOwner(User owner);

    //find by price between
    List<Transport> findAllBelowPrice(double price);
}
