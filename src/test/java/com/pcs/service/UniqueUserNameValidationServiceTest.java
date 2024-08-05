package com.pcs.service;

import com.pcs.model.User;
import com.pcs.web.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UniqueUserNameValidationServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UniqueUserNameValidationService uniqueUserNameValidationService;

    @Test
    public void should_return_empty_message_when_userName_is_not_already_used() {
        UserDTO userDTOToValidate = new UserDTO(null, "alan", "alan_password",
                "alan_fullname", "user");
        User userKen = new User(1, "ken", "ken_password",
                "ken_fullname", "user");
        User userAda = new User(2, "ada", "ada_password",
                "ada_fullname", "user");
        List<User> allUsersList = new ArrayList<>();
        allUsersList.add(userKen);
        allUsersList.add(userAda);
        when(userService.getUsers()).thenReturn(allUsersList);
        String message = uniqueUserNameValidationService.validateUserDTOUserName(userDTOToValidate);
        verify(userService).getUsers();
        assertEquals("", message);
    }

    @Test
    public void should_return_appropriate_message_when_userName_is_already_used() {
        UserDTO userDTOToValidate = new UserDTO(null, "ada", "password_of_ada_the_2nd",
                "fullname_of_ada_the_2nd", "user");
        User userKen = new User(1, "ken", "ken_password",
                "ken_fullname", "user");
        User userAda = new User(2, "ada", "ada_password",
                "ada_fullname", "user");
        List<User> allUsersList = new ArrayList<>();
        allUsersList.add(userKen);
        allUsersList.add(userAda);
        when(userService.getUsers()).thenReturn(allUsersList);
        String message = uniqueUserNameValidationService.validateUserDTOUserName(userDTOToValidate);
        verify(userService).getUsers();
        assertEquals("The UserName ada is already in use. " +
                "Please choose another one.", message);
    }

}
