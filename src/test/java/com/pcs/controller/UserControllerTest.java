package com.pcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.model.User;
import com.pcs.service.UniqueUserNameValidationService;
import com.pcs.service.UserService;
import com.pcs.web.dto.UserDTO;
import com.pcs.web.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private UniqueUserNameValidationService uniqueUserNameValidationService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user")
    public void should_return_to_user_List_page_successfully() throws Exception {
        //given
        UserDTO userDTO1 = new UserDTO(1, "username_1", "crypted_password_1","fullname_1",
                "user");
        UserDTO userDTO2 = new UserDTO(2, "username_2", "crypted_password_2","fullname_2",
                "user");;
        List<UserDTO> expectedUserDTOs = new ArrayList<>();
        expectedUserDTOs.add(userDTO1);
        expectedUserDTOs.add(userDTO2);
        when(userService.getUserDTOs()).thenReturn(expectedUserDTOs);
        //when
        mockMvc.perform(get("/user/list"))
                //then
                .andDo(print())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("userDTOs"))
                .andExpect(model().attribute("userDTOs", expectedUserDTOs))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_user_add_page_successfully() throws Exception {
        //given
        UserDTO expectedUserDTO = new UserDTO();
        //when
        mockMvc.perform(get("/user/add"))
                //then
                .andDo(print())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().attribute("userDTO", expectedUserDTO))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_save_valid_user_successfully() throws Exception {
        //given
        UserDTO userDTO1 = new UserDTO(null, "username_1", "aA!test","fullname_1",
                "user");
        User user1 = new User(null, "username_1",
                "$2a$10$GA6erqr7ckBGs9ka1Nou/OfG/fpUrqv12E.0NC0cTVyx/kvgrE43C","fullname_1",
                "user");
        when(uniqueUserNameValidationService.validateUserDTOUserName(any(UserDTO.class))).thenReturn("");
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(user1);
        doNothing().when(userService).save(user1);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = new UserDTO();
        //when
        mockMvc.perform(post("/user/validate").with(csrf())
                        .content(objectMapper.writeValueAsBytes(userDTO))
                        .param("username", "username_1")
                        .param("password", "aA!1test")
                        .param("fullname", "fullname_1")
                        .param("role", "user"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
        verify(userService).save(user1);
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_user_update_page_successfully() throws Exception {
        //given
        User user99 = new User(99, "username_99", "crypted_password_99","fullname_99",
                "user");
        UserDTO userDTO99 = new UserDTO(99, "username_99", "fullname_99", "user");
        when(userService.getById(99)).thenReturn(user99);
        when(userMapper.toUserDTO(user99)).thenReturn(userDTO99);
        //when
        mockMvc.perform(get("/user/update/99"))
                //then
                .andDo(print())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().attribute("userDTO", userDTO99))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_update_valid_user_successfully() throws Exception {
        //given
        UserDTO initialUserDTO99 = new UserDTO(99, "username_99", "fullname_99", "user");
        User user99 = new User(99, "username_9009","crypted_password_9009" ,"fullname_99",
                "user");
        //UserDTO userDTO99 = new UserDTO(99, "account_9009", "type_9009", "9009.9009");
        when(uniqueUserNameValidationService.validateUserDTOUserName(any(UserDTO.class))).thenReturn("");
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(user99);
        doNothing().when(userService).update(user99);
        ObjectMapper objectMapper = new ObjectMapper();
        //when
        mockMvc.perform(post("/user/update/99").with(csrf())
                        .content(objectMapper.writeValueAsBytes(initialUserDTO99))
                        .param("username", "username_9009")
                        .param("password", "aA!1test_9009")
                        .param("fullname", "fullname_9009")
                        .param("role", "user"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
        verify(userService).update(user99);
    }

    @Test
    @WithMockUser(username = "user")
    public void should_delete_user_successfully() throws Exception {
        //given
        doNothing().when(userService).deleteById(1);
        //when
        mockMvc.perform(get("/user/delete/1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
        verify(userService).deleteById(1);
    }

}
