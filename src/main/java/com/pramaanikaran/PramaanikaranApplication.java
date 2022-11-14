package com.pramaanikaran;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pramaanikaran.entity.AppRole;
import com.pramaanikaran.entity.AppUser;
import com.pramaanikaran.repository.AppRoleRepository;
import com.pramaanikaran.repository.AppUserRepository;

@SpringBootApplication
public class PramaanikaranApplication {

	public static void main(String[] args) {
		SpringApplication.run(PramaanikaranApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserRepository userRepository, AppRoleRepository roleRepository) {
		return args -> {
			AppRole role = roleRepository.save(AppRole.builder().name("ROLE_SUPER_ADMIN").build());
			userRepository.save(AppUser.builder()
					.firstName("John")
					.lastName("Wick")
					.email("john.wick@babayagaslayer.com")
					.roles(Set.of(role))
					.build());
		};
	}

}
