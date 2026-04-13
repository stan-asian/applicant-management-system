package com.lab.narra.ams.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lab.narra.ams.model.dto.UserDTO;
import com.lab.narra.ams.model.entity.User;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User DTOtoUser(UserDTO userDto) {
        return new User().setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setEmail(userDto.getEmail())
                .setPassword(passwordEncoder.encode(userDto.getPassword()));
    }

    // EXPOSED: Fname, Lname, Email
    public UserDTO UsertoDTO(User user) {
        // Dont return a password since it is obvious to not return a sensitive data
        // information in the controller
        return new UserDTO().setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail());
    }
}
