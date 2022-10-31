package com.project.TravelAgency.repo;

import com.project.TravelAgency.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsUserByEmail(String email);
    Page<User> findAll(Pageable pageable);

    @Query(value = "DELETE from user_roles where user_id= :user_id", nativeQuery = true)
    void deleteAllRolesForUser(@Param("user_id") Long user_id);
}
