package com.pramaanikaran.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pramaanikaran.entities.UserRoles;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
    
}
