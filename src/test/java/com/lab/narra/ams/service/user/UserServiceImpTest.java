package com.lab.narra.ams.service.user;

import com.lab.narra.ams.mapper.UserMapper;
import com.lab.narra.ams.model.dto.UserDTO;
import com.lab.narra.ams.model.entity.User;
import com.lab.narra.ams.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
   private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImp userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");

        userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
    }

    // Test saveUser
    @Test
    void testSaveUser() {
        when(userMapper.DTOtoUser(userDTO)).thenReturn(user);
 
        userService.saveUser(userDTO);

        verify(userMapper, times(1)).DTOtoUser(userDTO);
        verify(userRepository, times(1)).save(user);
    }

    // Test isUserExistByEmail
    @Test
    void testIsUserExistByEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = userService.isUserExistByEmail("test@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    // Test getUserByEmail (user exists)
    @Test
    void testGetUserByEmail_found() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.getUserByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    // Test getUserByEmail (user NOT found)
    @Test
    void testGetUserByEmail_notFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        User result = userService.getUserByEmail("test@example.com");

        assertNull(result);
    }

    // Test updatePassword
    @Test
    void testUpdatePassword() {
        String rawPassword = "newPassword123";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        userService.updatePassword(user, rawPassword);

        assertEquals(encodedPassword, user.getPassword());
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(userRepository, times(1)).save(user);
    }

    // Test updatePassword with null user
    @Test
    void testUpdatePassword_nullUser() {
        userService.updatePassword(null, "password");

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any());
    }
}