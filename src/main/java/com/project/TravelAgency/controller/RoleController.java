package com.project.TravelAgency.controller;

import com.project.TravelAgency.entity.ERole;
import com.project.TravelAgency.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("/*")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping({"/addRoleToUser"})
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void addRoleToUser(@RequestParam String email, @RequestParam String role){
        roleService.addRoleToUser(email, ERole.valueOf(role));
    }
}
