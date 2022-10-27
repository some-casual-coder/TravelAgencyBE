package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.ERole;
import com.project.TravelAgency.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
