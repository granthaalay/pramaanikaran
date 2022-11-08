package com.pramaanikaran.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pramaanikaran.entities.RolePrivileges;
import com.pramaanikaran.repositories.RolePrivilegesRepository;

@Service
@Transactional
public class RolePrivilegesService {
    @Autowired
    RolePrivilegesRepository rolePrivilegesRepository;

    public RolePrivileges addOrUpdatePrivileges(RolePrivileges rolePrivileges) {
        return rolePrivilegesRepository.save(rolePrivileges);
    }
}
