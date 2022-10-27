package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.ERole;
import com.project.TravelAgency.entity.Role;

public interface RoleService {
    Role createRole(Role role);
    void addRoleToUser(String email, ERole roleName);

    boolean deleteRole();
}
