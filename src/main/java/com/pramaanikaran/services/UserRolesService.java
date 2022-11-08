package com.pramaanikaran.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pramaanikaran.entities.UserRoles;
import com.pramaanikaran.repositories.UserRolesRepository;

@Service
@Transactional
public class UserRolesService {
    @Autowired
    UserRolesRepository userRolesRepository;

    public UserRoles addOrUpdateRole(UserRoles userRoles) {
        return userRolesRepository.save(userRoles);
    }
}
