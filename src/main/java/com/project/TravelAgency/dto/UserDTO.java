package com.project.TravelAgency.dto;

import com.project.TravelAgency.entity.Hotel;
import com.project.TravelAgency.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean verified;
    private boolean banned;
    private Set<Role> roles;
    private Set<Hotel> hotels;
}
