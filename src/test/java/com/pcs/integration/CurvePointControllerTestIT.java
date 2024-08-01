package com.pcs.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.controller.CurvePointController;
import com.pcs.repository.CurvePointRepository;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurvePointController.class)
public class CurvePointControllerTestIT {

    @MockBean
    private CurvePointRepository curvePointRepository;

    @Autowired
    private CurvePointController curvePointController;

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private CurvePointMapper curvePointMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    public void should_return_curvePoint_add_page_with_error_message_when_submit_non_valid_pattern_curvePoint()
            throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        CurvePointDTO curvePointDTO = new CurvePointDTO();
        //when
        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                        .content(objectMapper.writeValueAsBytes(curvePointDTO))
                        .param("curveId", "1")
                        .param("term", "lettersInsteadOfNumbers")
                        .param("value", "4"))
                //then
                .andDo(print())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("curvePointDTO", "term", "Pattern"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_curvePoint_add_page_with_error_message_when_submit_blank_field_curvePoint()
            throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        CurvePointDTO curvePointDTO = new CurvePointDTO();
        //when
        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                        .content(objectMapper.writeValueAsBytes(curvePointDTO))
                        .param("curveId", "")
                        .param("term", "3")
                        .param("value", "4"))
                //then
                .andDo(print())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("curvePointDTO", "curveId", "NotBlank"))
                .andExpect(status().is2xxSuccessful());
    }

}
