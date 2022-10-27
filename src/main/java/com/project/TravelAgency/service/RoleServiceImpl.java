package com.project.TravelAgency.service;

import com.project.TravelAgency.entity.ERole;
import com.project.TravelAgency.entity.Role;
import com.project.TravelAgency.entity.User;
import com.project.TravelAgency.repo.RoleRepo;
import com.project.TravelAgency.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Role createRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, ERole roleName) {
       User user = userRepo.findByEmail(email);
       Role role = roleRepo.findByName(roleName);
       user.getRoles().add(role);
    }

    @Override
    public boolean deleteRole() {
        return false;
    }
}
