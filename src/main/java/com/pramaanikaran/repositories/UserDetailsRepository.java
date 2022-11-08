package com.pramaanikaran.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pramaanikaran.entities.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    
}
