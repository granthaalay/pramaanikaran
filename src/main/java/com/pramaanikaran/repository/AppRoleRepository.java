package com.pramaanikaran.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pramaanikaran.entity.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole getRoleByName(String roleName);
}
