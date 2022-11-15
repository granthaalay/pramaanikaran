package com.pramaanikaran;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pramaanikaran.entity.AppRole;
import com.pramaanikaran.entity.AppUser;
import com.pramaanikaran.repository.AppRoleRepository;
import com.pramaanikaran.repository.AppUserRepository;

@SpringBootApplication
public class PramaanikaranApplication {

	public static void main(String[] args) {
		SpringApplication.run(PramaanikaranApplication.class, args);
	}

    @Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner run(AppUserRepository userRepository, AppRoleRepository roleRepository) {
		return args -> {
			AppRole role = roleRepository.save(AppRole.builder().name("ROLE_SUPERADMIN").build());
			userRepository.save(AppUser.builder()
					.firstName("John")
					.lastName("Wick")
					.email("john.wick@babayagaslayer.com")
					.password(passwordEncoder.encode("daisy"))
					.enabled(true)
					.accountNonLocked(true)
					.accountNonExpired(true)
					.credentialsNonExpired(true)
					.roles(Set.of(role))
					.build());

			role = roleRepository.save(AppRole.builder().name("ROLE_STUDENT").build());
			userRepository.save(AppUser.builder()
					.firstName("Mahendra")
					.lastName("Bahubali")
					.email("mbahubali@novice.com")
					.password(passwordEncoder.encode("kattapa"))
					.enabled(true)
					.accountNonLocked(true)
					.accountNonExpired(true)
					.credentialsNonExpired(true)
					.roles(Set.of(role))
					.build());	
		};
	}

}
