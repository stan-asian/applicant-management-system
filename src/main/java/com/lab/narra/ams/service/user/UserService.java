package com.lab.narra.ams.service.user;

import com.lab.narra.ams.model.dto.UserDTO;
import com.lab.narra.ams.model.entity.User;

public interface UserService {
    void saveUser(UserDTO userDto);
    boolean isUserExistByEmail(String email);
    User getUserByEmail(String email);
    void updatePassword(User user, String newPassword);
}
