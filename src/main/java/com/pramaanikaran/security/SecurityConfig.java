package com.pramaanikaran.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasAnyAuthority("ROLE_USER", "ROLE_SUPERADMIN")
                .antMatchers("/new").hasAnyAuthority("ROLE_SUPERADMIN")
                .antMatchers("/edit/**").hasAnyAuthority("ROLE_SUPERADMIN")
                .antMatchers("/delete/**").hasAuthority("ROLE_SUPERADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }
}
