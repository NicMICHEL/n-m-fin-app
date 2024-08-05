package com.pcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.model.CurvePoint;
import com.pcs.service.CurvePointService;
import com.pcs.web.dto.CurvePointDTO;
import com.pcs.web.mapper.CurvePointMapper;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurvePointController.class)
public class CurvePointControllerTest {

    @MockBean
    private CurvePointService curvePointService;
    @MockBean
    private CurvePointMapper curvePointMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user")
    public void should_return_to_curvePointList_page_successfully() throws Exception {
        //given
        CurvePointDTO curvePointDTO1 = new CurvePointDTO(1, "2", "2.2", "3.3");
        CurvePointDTO curvePointDTO2 = new CurvePointDTO(2, "3", "3.3", "4.4");
        List<CurvePointDTO> expectedCurvePointDTOs = new ArrayList<>();
        expectedCurvePointDTOs.add(curvePointDTO1);
        expectedCurvePointDTOs.add(curvePointDTO2);
        when(curvePointMapper.getCurvePointDTOs()).thenReturn(expectedCurvePointDTOs);
        //when
        mockMvc.perform(get("/curvePoint/list"))
                //then
                .andDo(print())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePointDTOs"))
                .andExpect(model().attribute("curvePointDTOs", expectedCurvePointDTOs))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_curvePoint_add_page_successfully() throws Exception {
        //given
        CurvePointDTO expectedCurvePointDTO = new CurvePointDTO();
        //when
        mockMvc.perform(get("/curvePoint/add"))
                //then
                .andDo(print())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePointDTO"))
                .andExpect(model().attribute("curvePointDTO", expectedCurvePointDTO))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_save_valid_curvePoint_successfully() throws Exception {
        //given
        CurvePointDTO curvePointDTO1 = new CurvePointDTO(null, "2", "2.2", "3.3");
        CurvePoint curvePoint1 = new CurvePoint(null, 2, 2.2, 3.3);
        when(curvePointMapper.toCurvePoint(curvePointDTO1)).thenReturn(curvePoint1);
        doNothing().when(curvePointService).save(curvePoint1);
        ObjectMapper objectMapper = new ObjectMapper();
        CurvePointDTO curvePointDTO = new CurvePointDTO();
        //when
        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                        .content(objectMapper.writeValueAsBytes(curvePointDTO))
                        .param("curveId", "2")
                        .param("term", "2.2")
                        .param("value", "3.3"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
        verify(curvePointService).save(curvePoint1);
        assertNotNull(curvePoint1.getCreationDate());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_curvePoint_update_page_successfully() throws Exception {
        //given
        CurvePoint curvePoint99 = new CurvePoint(99, 110, 22.22, 33.33);
        CurvePointDTO curvePointDTO99 = new CurvePointDTO(99, "110", "22.22", "33.33");
        when(curvePointService.getById(99)).thenReturn(curvePoint99);
        when(curvePointMapper.toCurvePointDTO(curvePoint99)).thenReturn(curvePointDTO99);
        //when
        mockMvc.perform(get("/curvePoint/update/99"))
                //then
                .andDo(print())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePointDTO"))
                .andExpect(model().attribute("curvePointDTO", curvePointDTO99))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_update_valid_curvePoint_successfully() throws Exception {
        //given
        CurvePointDTO initialCurvePointDTO99 = new CurvePointDTO(99, "110", "22.22", "33.33");
        CurvePoint curvePoint99 = new CurvePoint(99, 110, 2d, 3d);
        CurvePointDTO curvePointDTO99 = new CurvePointDTO(99, "110", "2", "3");
        when(curvePointMapper.toCurvePoint(curvePointDTO99)).thenReturn(curvePoint99);
        doNothing().when(curvePointService).update(curvePoint99);
        ObjectMapper objectMapper = new ObjectMapper();
        //when
        mockMvc.perform(post("/curvePoint/update/99").with(csrf())
                        .content(objectMapper.writeValueAsBytes(initialCurvePointDTO99))
                        .param("curveId", "110")
                        .param("term", "2")
                        .param("value", "3"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
        verify(curvePointService).update(curvePoint99);
    }

    @Test
    @WithMockUser(username = "user")
    public void should_delete_curvePoint_successfully() throws Exception {
        //given
        doNothing().when(curvePointService).deleteById(1);
        //when
        mockMvc.perform(get("/curvePoint/delete/1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
        verify(curvePointService).deleteById(1);
    }

}
