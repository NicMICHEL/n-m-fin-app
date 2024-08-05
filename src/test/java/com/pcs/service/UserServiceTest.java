package com.pcs.service;

import com.pcs.model.User;
import com.pcs.repository.UserRepository;
import com.pcs.web.dto.UserDTO;
import com.pcs.web.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    public void should_throw_an_exception_when_getting_user_corresponding_to_id_is_not_found() {
        //given
        Optional<User> emptyUser = Optional.empty();
        when(userRepository.findById(anyInt())).thenReturn(emptyUser);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    userService.getById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid user id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_updating_user_corresponding_to_id_is_not_found() {
        //given
        User testUser = new User();
        testUser.setId(55);
        Optional<User> emptyUser = Optional.empty();
        when(userRepository.findById(55)).thenReturn(emptyUser);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    userService.update(testUser);
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid user id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_deleting_user_corresponding_to_id_is_not_found() {
        //given
        Optional<User> emptyUser = Optional.empty();
        when(userRepository.findById(anyInt())).thenReturn(emptyUser);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    userService.deleteById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid user id", thrown.getMessage());
    }

    @Test
    public void should_get_userDTOs_successfully() throws Exception {
        //given
        User user1 = new User(1, "username_1", "crypted_password_1","fullname_1",
                "user");
        User user2 = new User(2, "username_2", "crypted_password_2","fullname_2",
                "user");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        UserDTO userDTO1 = new UserDTO(1, "username_1", "crypted_password_1","fullname_1",
                "user");
        UserDTO userDTO2 = new UserDTO(2, "username_2", "crypted_password_2","fullname_2",
                "user");;
        List<UserDTO> expectedUserDTOs = new ArrayList<>();
        expectedUserDTOs.add(userDTO1);
        expectedUserDTOs.add(userDTO2);
        when(userService.getUsers()).thenReturn(users);
        when(userMapper.toUserDTO(user1)).thenReturn(userDTO1);
        when(userMapper.toUserDTO(user2)).thenReturn(userDTO2);
        //when
        List<UserDTO> actualUserDTOs = userService.getUserDTOs();
        //then
        assertEquals(expectedUserDTOs, actualUserDTOs);
    }

}
