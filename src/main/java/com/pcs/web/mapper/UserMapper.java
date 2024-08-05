package com.pcs.web.mapper;

import com.pcs.model.User;
import com.pcs.web.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

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

}
