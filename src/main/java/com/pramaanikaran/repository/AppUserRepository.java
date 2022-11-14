package com.pramaanikaran.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pramaanikaran.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser getUserByEmail(String email);
}
