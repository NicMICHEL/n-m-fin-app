package com.pcs.web.mapper;

import com.pcs.model.User;
import com.pcs.service.UserService;
import com.pcs.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private UserService userService;

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getRole()
        );
    }

    public User toUser(UserDTO userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                encoder.encode(userDTO.getPassword()),
                userDTO.getFullname(),
                userDTO.getRole()
        );
    }

    public List<UserDTO> getUserDTOs() {
        List<User> users = userService.getUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> {
            userDTOs.add(toUserDTO(user));
        });
        return userDTOs;
    }

}
