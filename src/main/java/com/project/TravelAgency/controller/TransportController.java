package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.TransportDTO;
import com.project.TravelAgency.dto.UserDTO;
import com.project.TravelAgency.entity.Transport;
import com.project.TravelAgency.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TransportController {
    @Autowired
    private ModelMapper modelMapper;

    private TransportDTO convertToDto(Transport transport){
        return modelMapper.map(transport, TransportDTO.class);
    }

    private Transport convertToEntity(TransportDTO transportDTO){
        return modelMapper.map(transportDTO, Transport.class);
    }
}
