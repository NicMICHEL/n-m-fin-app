package com.pcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.model.BidList;
import com.pcs.service.BidListService;
import com.pcs.web.dto.BidListDTO;
import com.pcs.web.mapper.BidListMapper;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private BidListController bidListController;
    @MockBean
    private BidListService bidListService;
    @MockBean
    private BidListMapper bidListMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user")
    public void should_return_to_bidList_List_page_successfully() throws Exception {
        //given
        BidList bidList1 = new BidList(1, "account_1", "type_1", 1.1);
        BidList bidList2 = new BidList(2, "account_2", "type_2", 2.2);
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(bidList1);
        bidLists.add(bidList2);
        BidListDTO bidListDTO1 = new BidListDTO(1, "account_1", "type_1", "1.1");
        BidListDTO bidListDTO2 = new BidListDTO(2, "account_2", "type_2", "2.2");
        List<BidListDTO> expectedBidListDTOs = new ArrayList<>();
        expectedBidListDTOs.add(bidListDTO1);
        expectedBidListDTOs.add(bidListDTO2);
        when(bidListService.getBidLists()).thenReturn(bidLists);
        when(bidListMapper.toBidListDTO(bidList1)).thenReturn(bidListDTO1);
        when(bidListMapper.toBidListDTO(bidList2)).thenReturn(bidListDTO2);
        //when
        mockMvc.perform(get("/bidList/list"))
                //then
                .andDo(print())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidListDTOs"))
                .andExpect(model().attribute("bidListDTOs", expectedBidListDTOs))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_bidList_add_page_successfully() throws Exception {
        //given
        BidListDTO expectedBidListDTO = new BidListDTO();
        //when
        mockMvc.perform(get("/bidList/add"))
                //then
                .andDo(print())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidListDTO"))
                .andExpect(model().attribute("bidListDTO", expectedBidListDTO))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_save_valid_bidList_successfully() throws Exception {
        //given
        BidListDTO bidListDTO1 = new BidListDTO(null, "account_1", "type_1", "1.1");
        BidList bidList1 = new BidList(null, "account_1", "type_1", 1.1);
        when(bidListMapper.toBidList(bidListDTO1)).thenReturn(bidList1);
        doNothing().when(bidListService).save(bidList1);
        ObjectMapper objectMapper = new ObjectMapper();
        BidListDTO bidListDTO = new BidListDTO();
        //when
        mockMvc.perform(post("/bidList/validate").with(csrf())
                        .content(objectMapper.writeValueAsBytes(bidListDTO))
                        .param("account", "account_1")
                        .param("type", "type_1")
                        .param("bidQuantity", "1.1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
        verify(bidListService).save(bidList1);
        assertNotNull(bidList1.getCreationDate());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_bidList_update_page_successfully() throws Exception {
        //given
        BidList bidList99 = new BidList(99, "account_99", "type_99", 99.99);
        BidListDTO bidListDTO99 = new BidListDTO(99, "account_99", "type_99", "99.99");
        when(bidListService.getById(99)).thenReturn(bidList99);
        when(bidListMapper.toBidListDTO(bidList99)).thenReturn(bidListDTO99);
        //when
        mockMvc.perform(get("/bidList/update/99"))
                //then
                .andDo(print())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidListDTO"))
                .andExpect(model().attribute("bidListDTO", bidListDTO99))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_update_valid_bidList_successfully() throws Exception {
        //given
        BidListDTO initialBidListDTO99 = new BidListDTO(99, "account_99", "type_99", "99.99");
        BidList bidList99 = new BidList(99, "account_9009", "type_9009", 9009.9009);
        //BidListDTO bidListDTO99 = new BidListDTO(99, "account_9009", "type_9009", "9009.9009");
        when(bidListMapper.toBidList(any(BidListDTO.class))).thenReturn(bidList99);
        doNothing().when(bidListService).update(bidList99);
        ObjectMapper objectMapper = new ObjectMapper();
        //when
        mockMvc.perform(post("/bidList/update/99").with(csrf())
                        .content(objectMapper.writeValueAsBytes(initialBidListDTO99))
                        .param("account", "account_9009")
                        .param("type", "type_9009")
                        .param("bidQuantity", "9009.9009"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
        verify(bidListService).update(bidList99);
        assertNotNull(bidList99.getRevisionDate());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_delete_bidList_successfully() throws Exception {
        //given
        doNothing().when(bidListService).deleteById(1);
        //when
        mockMvc.perform(get("/bidList/delete/1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
        verify(bidListService).deleteById(1);
    }

    @Test
    public void should_get_bidListDTOs_successfully() throws Exception {
        //given
        BidList bidList1 = new BidList(1, "account_1", "type_1", 1.1);
        BidList bidList2 = new BidList(2, "account_2", "type_2", 2.2);
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(bidList1);
        bidLists.add(bidList2);
        BidListDTO bidListDTO1 = new BidListDTO(1, "account_1", "type_1", "1.1");
        BidListDTO bidListDTO2 = new BidListDTO(2, "account_2", "type_2", "2.2");
        List<BidListDTO> expectedBidListDTOs = new ArrayList<>();
        expectedBidListDTOs.add(bidListDTO1);
        expectedBidListDTOs.add(bidListDTO2);
        when(bidListService.getBidLists()).thenReturn(bidLists);
        when(bidListMapper.toBidListDTO(bidList1)).thenReturn(bidListDTO1);
        when(bidListMapper.toBidListDTO(bidList2)).thenReturn(bidListDTO2);
        //when
        List<BidListDTO> actualBidListDTOs = bidListController.getBidListDTOs();
        //then
        assertEquals(expectedBidListDTOs, actualBidListDTOs);
    }

}
