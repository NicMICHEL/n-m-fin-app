package com.pcs.web.mapper;

import com.pcs.model.User;
import com.pcs.service.UserService;
import com.pcs.web.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;
    @Mock
    private UserService userService;


    @Test
    public void should_get_userDTOs_successfully() throws Exception {
        //given
        User user1 = new User(1, "username_1", "crypted_password_1", "fullname_1",
                "user");
        User user2 = new User(2, "username_2", "crypted_password_2", "fullname_2",
                "user");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        UserDTO userDTO1 = new UserDTO(1, "username_1", null, "fullname_1",
                "user");
        UserDTO userDTO2 = new UserDTO(2, "username_2", null, "fullname_2",
                "user");
        List<UserDTO> expectedUserDTOs = new ArrayList<>();
        expectedUserDTOs.add(userDTO1);
        expectedUserDTOs.add(userDTO2);
        when(userService.getUsers()).thenReturn(users);
        //when
        List<UserDTO> actualUserDTOs = userMapper.getUserDTOs();
        //then
        assertEquals(expectedUserDTOs, actualUserDTOs);
    }

}
