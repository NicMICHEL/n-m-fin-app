package com.pcs.controller;

import com.pcs.configuration.ConnectedUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConnectedUser connectedUser;

    @Test
    @WithMockUser(username = "user")
    public void should_return_login_page() throws Exception {
        //when
        mockMvc.perform(get("/showLoginPage"))
                //then
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_accessDenied_page() throws Exception {
        //given
        String errorMessage = "You are not authorized for the requested data.";
        when(connectedUser.getUsernamePasswordLoginInfo(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn("albert");
        //when
        mockMvc.perform(get("/accessDeniedPage"))
                //then
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("403"))
                .andExpect(model().attributeExists("errorMsg"))
                .andExpect(model().attribute("errorMsg", errorMessage))
                .andExpect(model().attributeExists("connectedUserName"))
                .andExpect(model().attribute("connectedUserName", "albert"));
    }

}
