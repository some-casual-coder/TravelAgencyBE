package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.Image;
import com.project.TravelAgency.entity.Transport;
import com.project.TravelAgency.entity.TransportAddOn;
import com.project.TravelAgency.repo.ImageRepo;
import com.project.TravelAgency.repo.TransportAddOnRepo;
import com.project.TravelAgency.repo.TransportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransportServiceImpl implements TransportService{

    @Autowired
    private TransportRepo transportRepo;

    @Autowired
    private TransportAddOnRepo addOnRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Override
    public Transport addMeansOfTransport(Transport transport) {
        return transportRepo.save(transport);
    }

    @Override
    public Transport updateMeansOfTransport(Transport transport) {
        return transportRepo.save(transport);
    }

    @Override
    public void deleteById(Long id) {
        transportRepo.deleteById(id);
    }

    @Override
    public TransportAddOn addTransportAddOn(TransportAddOn transportAddOn) {
        return addOnRepo.save(transportAddOn);
    }

    @Override
    public TransportAddOn updateAddOn(TransportAddOn transportAddOn) {
        return addOnRepo.save(transportAddOn);
    }

    @Override
    public void deleteAddOn(Long id) {
        addOnRepo.deleteById(id);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepo.deleteById(id);
    }

    @Override
    public Image addTransportImage(Image image) {
        image.setHotel(null);
        image.setRoom(null);
        return imageRepo.save(image);
    }

    @Override
    public Page<Transport> findAll(Pageable pageable) {
        return transportRepo.findAll(pageable);
    }

    //Example Usage
//    public static void changeUserName(String oldFirstName, String newFirstName) {
//        findUserByFirstName(oldFirstName).ifPresent(user -> user.setFirstName(newFirstName));
//    }
    @Override
    public Optional<Transport> findById(Long id) {
        return transportRepo.findById(id);
    }

    @Override
    public List<Transport> findByLatLng(Double lat, Double lng) {
        return transportRepo.findByLatLng(--lat, ++lat, --lng, ++lng);
    }

    @Override
    public List<Transport> findByTransportType(Long transportType) {
        return transportRepo.findByTransportType(transportType);
    }

    @Override
    public List<Transport> findByCapacity(int capacity) {
        return transportRepo.findByCapacity(capacity);
    }

    @Override
    public List<Image> findAllTransportImages(Long transportId) {
        return transportRepo.findAllTransportImages(transportId);
    }

    @Override
    public List<TransportAddOn> findAllAddOns(Long transportId) {
        return transportRepo.findAllAddOns(transportId);
    }

    @Override
    public List<Transport> findByOwner(Long owner) {
        return transportRepo.findByOwner(owner);
    }

    @Override
    public List<Transport> findAllBelowPrice(double price) {
        return transportRepo.findAllBelowPrice(price);
    }
}
