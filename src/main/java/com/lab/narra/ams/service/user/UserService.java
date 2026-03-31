package com.lab.narra.ams.service.user;

import com.lab.narra.ams.model.dto.UserDTO;

public interface UserService {
    void saveUser(UserDTO userDto);
    boolean isUserExistByEmail(String email);
    UserDTO getUserByEmail(String email);
}
