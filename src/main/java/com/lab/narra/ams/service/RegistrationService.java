package com.lab.narra.ams.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lab.narra.ams.model.dto.UserRegistrationDTO;
import com.lab.narra.ams.model.entity.User;
import com.lab.narra.ams.repository.UserRepository;

@Service
public class RegistrationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isEmailExisting(String email){
        return userRepository.existsByEmail(email);
    }

    public void saveUser(UserRegistrationDTO userDTO){

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));


        userRepository.save(user);
    }

}
