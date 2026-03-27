package com.lab.narra.ams.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lab.narra.ams.service.CustomUserDetailsService;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/visitor/**").permitAll()
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().authenticated())
                .userDetailsService(userDetailsService) // 👈 THIS is the key connection
                .formLogin(form -> form
                        .loginPage("/visitor/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/user/dashboard", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
}