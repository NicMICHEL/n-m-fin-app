package com.pcs.service;

import com.pcs.model.User;
import com.pcs.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
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
    public void should_throw_an_exception_when_getting_user_corresponding_to_username_is_not_found() {
        //given
        Optional<User> emptyUser = Optional.empty();
        when(userRepository.getUserByUsername(anyString())).thenReturn(emptyUser);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    userService.getUserByUsername(anyString());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid user username", thrown.getMessage());
    }

}
