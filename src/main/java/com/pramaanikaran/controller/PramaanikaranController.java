package com.pramaanikaran.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pramaanikaran.entity.AppRole;
import com.pramaanikaran.entity.AppUser;
import com.pramaanikaran.model.AppRoleDTO;
import com.pramaanikaran.model.AppUserDTO;
import com.pramaanikaran.repository.AppRoleRepository;
import com.pramaanikaran.repository.AppUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PramaanikaranController {
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AppRoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<AppUserDTO> signUp(AppUserDTO user) {
        log.info("adding new user with email " + user.getEmail());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser userEntity = AppUser.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .build();
        AppUser createdUser = userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(AppUserDTO.builder()
                .firstName(createdUser.getFirstName())
                .lastName(createdUser.getLastName())
                .email(createdUser.getEmail())
                .build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUserDTO>> getUsers() {
        log.info("fetching all users from database...");
        List<AppUser> users = userRepository.findAll();
        List<AppUserDTO> usersDto = users.stream().map(user -> {
            return AppUserDTO.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();
        }).toList();
        return ResponseEntity.ok().body(usersDto);
    }

    @PostMapping("/user/addrole")
    public ResponseEntity<String> addRoleToUser(String email, String roleName) {
        // fetch user and role
        AppUser user = userRepository.getUserByEmail(email);
        if(user == null) {
            log.warn("user %s not found".formatted(email));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user with email %s does not exist".formatted(email));
        }

        AppRole role = roleRepository.getRoleByName(roleName);
        if(role == null) {
            log.warn("role %s not found".formatted(roleName));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("role %s does not exist".formatted(roleName));
        }

        // update the role and save
        log.info("updating user %s with role %s".formatted(email, roleName));
        user.setRoles(Set.of(role));
        userRepository.save(user);

        return ResponseEntity.ok().body("Role %s added to user %s".formatted(roleName, email));
    }

    @PostMapping("/addrole")
    public ResponseEntity<AppRoleDTO> addRole(AppRoleDTO role) {
        log.info("creating new role %s".formatted(role.getName()));
        AppRole addedRole = roleRepository.save(AppRole.builder()
                .name(role.getName())
                .build());
        return ResponseEntity.ok().body(AppRoleDTO.builder()
                .name(addedRole.getName())
                .build());
    }
}
