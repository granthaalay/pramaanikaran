package com.pramaanikaran.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pramaanikaran.entities.UserDetails;
import com.pramaanikaran.repositories.UserDetailsRepository;

@Service
@Transactional
public class UserDetailsService {
    @Autowired
    UserDetailsRepository userDetailsRepository;

    public UserDetails addOrUpdateUser(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
}
