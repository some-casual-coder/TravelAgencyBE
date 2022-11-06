package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.*;
import com.project.TravelAgency.entity.*;
import com.project.TravelAgency.service.TransportService;
import com.project.TravelAgency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.project.TravelAgency.controller.HotelController.convertToImageDto;
import static com.project.TravelAgency.controller.HotelController.convertToImageEntity;

@RestController
@CrossOrigin("/*")
@Slf4j
public class TransportController {
    @Autowired
    private static ModelMapper modelMapper;

    @Autowired
    private TransportService transportService;

    @Autowired
    private UserService userService;

    //add means of transport
    @PostMapping({"/transport/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public TransportDTO addMeansOfTransport(@RequestBody TransportDTO transportDTO) {
        Transport transport = convertToTransportEntity(transportDTO);
        return convertToTransportDto(transportService.addMeansOfTransport(transport));
    }

    //find by id
    @GetMapping({"/transport/get"})
    public TransportDTO findById(@RequestParam Long transportId) {
        return convertToTransportDto(transportService.findById(transportId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(transportId))));
    }

    //update means of transport
    @PutMapping({"/transport/update"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public TransportDTO updateTransport(@RequestBody TransportDTO transportDTO) {
        Transport transport = convertToTransportEntity(transportDTO);
        return convertToTransportDto(transportService.addMeansOfTransport(transport));
    }

    //delete by id
    @DeleteMapping({"/transport/delete"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_SUPER_ADMIN')")
    public void deleteMeansOfTransport(@RequestParam Long transportId) {
        transportService.deleteById(transportId);
    }

    //find by owner => What is the use-case for this???
    @GetMapping({"/transport/all/owner"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public List<Transport> findAllByOwner(@RequestParam Long ownerId){
        User owner = userService.findById(ownerId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(ownerId)));
        return transportService.findByOwner(owner);
    }

    //add addon
    @PostMapping({"/transport/addon/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public TransportAddOnDTO addAddon(@RequestBody TransportAddOnDTO transportAddOnDTO) {
        TransportAddOn transportAddOn = convertToAddonEntity(transportAddOnDTO);
        return convertToAddonDto(transportService.addTransportAddOn(transportAddOn));
    }

    //update addon
    @PutMapping({"/transport/addon/update"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public TransportAddOnDTO updateAddon(@RequestBody TransportAddOnDTO transportAddOnDTO) {
        TransportAddOn transportAddOn = convertToAddonEntity(transportAddOnDTO);
        return convertToAddonDto(transportService.updateAddOn(transportAddOn));
    }

    //find all addons for transport means
    @GetMapping({"/transport/addon/all"})
    public List<TransportAddOn> findAllTransportAddons(@RequestParam Long transportId){
        Transport transport = transportService.findById(transportId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(transportId)));
        return transportService.findAllTransportAddOns(transport);
    }

    //delete addon
    @DeleteMapping({"/transport/addon/delete"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public void deleteAddon(@RequestParam Long addonId){
        transportService.deleteAddOn(addonId);
    }

    @PostMapping({"/transport/type/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public TransportTypeDTO addTransportType(@RequestBody TransportTypeDTO transportTypeDTO){
        return convertToTypeDto(transportService.addTransportType(convertToTypeEntity(transportTypeDTO)));
    }

    @GetMapping({"/transport/type/all"})
    public List<TransportType> findAllTypes(){
        return transportService.findAllTypes();
    }

    //add image
    @PostMapping({"/transport/image/add"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ImageDTO addTransportImage(@RequestBody ImageDTO imageDTO) {
        Image image = convertToImageEntity(imageDTO);
        return convertToImageDto(transportService.addTransportImage(image));
    }

    //find all images for transport means
    @GetMapping({"/transport/image/all"})
    public List<Image> findAllTransportImages(@RequestParam Long transportId) {
        return transportService.findAllTransportImages(transportId);
    }

    //delete image
    @DeleteMapping({"/transport/image/delete"})
    @PreAuthorize("hasAnyRole('ROLE_HOST','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public void deleteTransportMeansImage(@RequestParam Long imageId) {
        transportService.deleteImage(imageId);
    }

    //find all means of transport => page
    @GetMapping({"/transport/all"})
    public Page<Transport> findAllMeansOfTransport(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("capacity").ascending());
        return transportService.findAll(pageable);
    }

    //find all by coordinates
    @GetMapping({"/transport/all/nearby"})
    public List<Transport> findAllNearby(@RequestParam Double lat, @RequestParam Double lng) {
        return transportService.findByLatLng(lat, lng);
    }

    //find all by type
    @GetMapping({"/transport/all/type"})
    public List<Transport> findByType(@RequestParam Long typeId) {
        return transportService.findByTransportType(typeId);
    }

    //find all by capacity
    @GetMapping({"/transport/all/capacity"})
    public List<Transport> findAboveCapacity(@RequestParam int capacity) {
        return transportService.findByCapacity(capacity);
    }

    //find all below price
    @GetMapping({"/transport/all/price"})
    public List<Transport> findBelowPrice(@RequestParam double price) {
        return transportService.findAllBelowPrice(price);
    }

    protected static TransportDTO convertToTransportDto(Transport transport){
        return modelMapper.map(transport, TransportDTO.class);
    }

    protected static Transport convertToTransportEntity(TransportDTO transportDTO){
        return modelMapper.map(transportDTO, Transport.class);
    }

    private TransportAddOnDTO convertToAddonDto(TransportAddOn transportAddOn){
        return modelMapper.map(transportAddOn, TransportAddOnDTO.class);
    }

    private TransportAddOn convertToAddonEntity(TransportAddOnDTO transportAddOnDTO){
        return modelMapper.map(transportAddOnDTO, TransportAddOn.class);
    }

    private TransportTypeDTO convertToTypeDto(TransportType transportType){
        return modelMapper.map(transportType, TransportTypeDTO.class);
    }

    private TransportType convertToTypeEntity(TransportTypeDTO transportTypeDTO){
        return modelMapper.map(transportTypeDTO, TransportType.class);
    }
}
