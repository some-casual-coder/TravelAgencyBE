package com.project.TravelAgency.controller;

import com.project.TravelAgency.dto.RoomDTO;
import com.project.TravelAgency.dto.UserDTO;
import com.project.TravelAgency.entity.Room;
import com.project.TravelAgency.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomController {
    @Autowired
    private ModelMapper modelMapper;

    private RoomDTO convertToDto(Room room){
        return modelMapper.map(room, RoomDTO.class);
    }

    private Room convertToEntity(RoomDTO roomDTO){
        return modelMapper.map(roomDTO, Room.class);
    }
}
