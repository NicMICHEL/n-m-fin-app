package com.pcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.configuration.ConnectedUser;
import com.pcs.model.RuleName;
import com.pcs.service.RuleNameService;
import com.pcs.web.dto.RuleNameDTO;
import com.pcs.web.mapper.RuleNameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @MockBean
    private RuleNameService ruleNameService;
    @MockBean
    private RuleNameMapper ruleNameMapper;
    @MockBean
    private ConnectedUser connectedUser;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user")
    public void should_return_to_ruleName_List_page_successfully() throws Exception {
        //given
        RuleNameDTO ruleNameDTO1 = new RuleNameDTO(1, "name_1", "description_1", "json_1",
                "template_1", "sqlStr_1", "sqlPart_1");
        RuleNameDTO ruleNameDTO2 = new RuleNameDTO(2, "name_2", "description_2", "json_2",
                "template_2", "sqlStr_2", "sqlPart_2");
        List<RuleNameDTO> expectedRuleNameDTOs = new ArrayList<>();
        expectedRuleNameDTOs.add(ruleNameDTO1);
        expectedRuleNameDTOs.add(ruleNameDTO2);
        when(ruleNameMapper.getRuleNameDTOs()).thenReturn(expectedRuleNameDTOs);
        when(connectedUser.getUsernamePasswordLoginInfo(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn("albert");
        when(connectedUser.hasRole(any(UsernamePasswordAuthenticationToken.class), anyString())).thenReturn(true);
        //when
        mockMvc.perform(get("/ruleName/list"))
                //then
                .andDo(print())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNameDTOs"))
                .andExpect(model().attribute("ruleNameDTOs", expectedRuleNameDTOs))
                .andExpect(model().attributeExists("connectedUserName"))
                .andExpect(model().attribute("connectedUserName", "albert"))
                .andExpect(model().attributeExists("hasRoleAdmin"))
                .andExpect(model().attribute("hasRoleAdmin", true))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_ruleName_add_page_successfully() throws Exception {
        //given
        RuleNameDTO expectedRuleNameDTO = new RuleNameDTO();
        //when
        mockMvc.perform(get("/ruleName/add"))
                //then
                .andDo(print())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleNameDTO"))
                .andExpect(model().attribute("ruleNameDTO", expectedRuleNameDTO))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_save_valid_ruleName_successfully() throws Exception {
        //given
        RuleNameDTO ruleNameDTO1 = new RuleNameDTO(null, "name_1", "description_1", "json_1",
                "template_1", "sqlStr_1", "sqlPart_1");
        RuleName ruleName1 = new RuleName(null, "name_1", "description_1", "json_1",
                "template_1", "sqlStr_1", "sqlPart_1");
        when(ruleNameMapper.toRuleName(ruleNameDTO1)).thenReturn(ruleName1);
        doNothing().when(ruleNameService).save(ruleName1);
        ObjectMapper objectMapper = new ObjectMapper();
        RuleNameDTO ruleNameDTO = new RuleNameDTO();
        //when
        mockMvc.perform(post("/ruleName/validate").with(csrf())
                        .content(objectMapper.writeValueAsBytes(ruleNameDTO))
                        .param("name", "name_1")
                        .param("description", "description_1")
                        .param("json", "json_1")
                        .param("template", "template_1")
                        .param("sqlStr", "sqlStr_1")
                        .param("sqlPart", "sqlPart_1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
        verify(ruleNameService).save(ruleName1);
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_ruleName_update_page_successfully() throws Exception {
        //given
        RuleName ruleName99 = new RuleName(99, "name_99", "description_99", "json_99",
                "template_99", "sqlStr_99", "sqlPart_99");
        RuleNameDTO ruleNameDTO99 = new RuleNameDTO(99, "name_99", "description_99", "json_99",
                "template_99", "sqlStr_99", "sqlPart_99");
        when(ruleNameService.getById(99)).thenReturn(ruleName99);
        when(ruleNameMapper.toRuleNameDTO(ruleName99)).thenReturn(ruleNameDTO99);
        //when
        mockMvc.perform(get("/ruleName/update/99"))
                //then
                .andDo(print())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleNameDTO"))
                .andExpect(model().attribute("ruleNameDTO", ruleNameDTO99))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_update_valid_ruleName_successfully() throws Exception {
        //given
        RuleNameDTO initialRuleNameDTO99 = new RuleNameDTO(99, "name_99", "description_99",
                "json_99","template_99", "sqlStr_99", "sqlPart_99");
        RuleName ruleName99 = new RuleName(99, "name_9009", "description_9009", "json_9009",
                "template_9009", "sqlStr_9009", "sqlPart_9009");
        when(ruleNameMapper.toRuleName(any(RuleNameDTO.class))).thenReturn(ruleName99);
        doNothing().when(ruleNameService).update(ruleName99);
        ObjectMapper objectMapper = new ObjectMapper();
        //when
        mockMvc.perform(post("/ruleName/update/99").with(csrf())
                        .content(objectMapper.writeValueAsBytes(initialRuleNameDTO99))
                        .param("name", "name_9009")
                        .param("description", "description_9009")
                        .param("json", "json_9009")
                        .param("template", "template_9009")
                        .param("sqlStr", "sqlStr_9009")
                        .param("sqlPart", "sqlPart_9009"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
        verify(ruleNameService).update(ruleName99);
    }

    @Test
    @WithMockUser(username = "user")
    public void should_delete_ruleName_successfully() throws Exception {
        //given
        doNothing().when(ruleNameService).deleteById(1);
        //when
        mockMvc.perform(get("/ruleName/delete/1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
        verify(ruleNameService).deleteById(1);
    }

}
