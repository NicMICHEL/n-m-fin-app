package com.pcs.service;

import com.pcs.model.User;
import com.pcs.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniqueUserNameValidationService {

    @Autowired
    private UserService userService;

    public String validateUserDTOUserName(UserDTO userDTOToValidate) {
        List<User> allUsersList = userService.getUsers();
        List<String> userNamesUsersList = new ArrayList<>();
        for (User user : allUsersList) {
            userNamesUsersList.add(user.getUsername());
        }
        String message = "";
        if (userNamesUsersList.contains(userDTOToValidate.getUsername())) {
            message = "UserName " + userDTOToValidate.getUsername() + " is already in use. " +
                    "Please choose another one.";
        }
        return message;
    }

}
