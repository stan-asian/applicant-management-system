package com.lab.narra.ams.service.user;

import com.lab.narra.ams.mapper.UserMapper;
import com.lab.narra.ams.model.dto.UserDTO;
import com.lab.narra.ams.model.entity.User;
import com.lab.narra.ams.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDto){
        User user = userMapper.DTOtoUser(userDto);
        userRepository.save(user);
    }

    @Override
    public boolean isUserExistByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user != null){
            return user;
        }
        return null;
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        if (user != null) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }
    
}
