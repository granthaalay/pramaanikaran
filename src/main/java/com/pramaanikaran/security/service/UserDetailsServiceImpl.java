package com.pramaanikaran.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pramaanikaran.entity.AppUser;
import com.pramaanikaran.repository.AppUserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("user with email %s does not exist".formatted(email));
        }

        return new UserDetailsImpl(user, user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName())).toList());
    }

}
