package com.pramaanikaran.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pramaanikaran.entity.AppRole;
import com.pramaanikaran.entity.AppUser;
import com.pramaanikaran.model.AppRoleDTO;
import com.pramaanikaran.model.AppUserDTO;
import com.pramaanikaran.repository.AppRoleRepository;
import com.pramaanikaran.repository.AppUserRepository;
import com.pramaanikaran.security.service.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PramaanikaranController {
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AppRoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.app.jwt.validity}")
    private final long validityLength = 0;

    @PostMapping("/signin")
    // @PermitAll
    public ResponseEntity<?> signin(@RequestBody AppUserDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // List<String> roles = userDetails.getAuthorities().stream()
        //         .map(item -> item.getAuthority())
        //         .collect(Collectors.toList());

        Instant issuedAt = Instant.now();

        return ResponseEntity.ok(Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(issuedAt.toEpochMilli()))
                .setExpiration(new Date(issuedAt.toEpochMilli() + validityLength)));
    }

    @PostMapping("/signup")
    public ResponseEntity<AppUserDTO> signUp(AppUserDTO user) {
        log.info("adding new user with email " + user.getEmail());
        AppUser userEntity = AppUser.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        AppUser createdUser = userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(AppUserDTO.builder()
                .firstName(createdUser.getFirstName())
                .lastName(createdUser.getLastName())
                .email(createdUser.getEmail())
                .build());
    }

    @GetMapping("/users")
    // @PreAuthorize("hasPermission('ROLE_SUPERADMIN')")
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
        if (user == null) {
            log.warn("user %s not found".formatted(email));
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("user with email %s does not exist".formatted(email));
        }

        AppRole role = roleRepository.getRoleByName(roleName);
        if (role == null) {
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
    public ResponseEntity<AppRoleDTO> addRole(@RequestBody AppRoleDTO role) {
        log.info("creating new role %s".formatted(role.getName()));
        AppRole addedRole = roleRepository.save(AppRole.builder()
                .name(role.getName())
                .build());
        return ResponseEntity.ok().body(AppRoleDTO.builder()
                .name(addedRole.getName())
                .build());
    }
}
