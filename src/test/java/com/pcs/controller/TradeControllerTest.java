package com.pcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.model.Trade;
import com.pcs.service.TradeService;
import com.pcs.web.dto.TradeDTO;
import com.pcs.web.mapper.TradeMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private TradeController tradeController;
    @MockBean
    private TradeService tradeService;
    @MockBean
    private TradeMapper tradeMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user")
    public void should_return_to_trade_List_page_successfully() throws Exception {
        //given
        Trade trade1 = new Trade(1, "account_1", "type_1", 1.1);
        Trade trade2 = new Trade(2, "account_2", "type_2", 2.2);
        List<Trade> trades = new ArrayList<>();
        trades.add(trade1);
        trades.add(trade2);
        TradeDTO tradeDTO1 = new TradeDTO(1, "account_1", "type_1", "1.1");
        TradeDTO tradeDTO2 = new TradeDTO(2, "account_2", "type_2", "2.2");
        List<TradeDTO> expectedTradeDTOs = new ArrayList<>();
        expectedTradeDTOs.add(tradeDTO1);
        expectedTradeDTOs.add(tradeDTO2);
        when(tradeService.getTrades()).thenReturn(trades);
        when(tradeMapper.toTradeDTO(trade1)).thenReturn(tradeDTO1);
        when(tradeMapper.toTradeDTO(trade2)).thenReturn(tradeDTO2);
        //when
        mockMvc.perform(get("/trade/list"))
                //then
                .andDo(print())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("tradeDTOs"))
                .andExpect(model().attribute("tradeDTOs", expectedTradeDTOs))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_trade_add_page_successfully() throws Exception {
        //given
        TradeDTO expectedTradeDTO = new TradeDTO();
        //when
        mockMvc.perform(get("/trade/add"))
                //then
                .andDo(print())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("tradeDTO"))
                .andExpect(model().attribute("tradeDTO", expectedTradeDTO))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @WithMockUser(username = "user")
    public void should_save_valid_trade_successfully() throws Exception {
        //given
        TradeDTO tradeDTO1 = new TradeDTO(null, "account_1", "type_1", "1.1");
        Trade trade1 = new Trade(null, "account_1", "type_1", 1.1);
        when(tradeMapper.toTrade(tradeDTO1)).thenReturn(trade1);
        doNothing().when(tradeService).save(trade1);
        ObjectMapper objectMapper = new ObjectMapper();
        TradeDTO tradeDTO = new TradeDTO();
        //when
        mockMvc.perform(post("/trade/validate").with(csrf())
                        .content(objectMapper.writeValueAsBytes(tradeDTO))
                        .param("account", "account_1")
                        .param("type", "type_1")
                        .param("buyQuantity", "1.1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
        verify(tradeService).save(trade1);
        assertNotNull(trade1.getCreationDate());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_trade_update_page_successfully() throws Exception {
        //given
        Trade trade99 = new Trade(99, "account_99", "type_99", 99.99);
        TradeDTO tradeDTO99 = new TradeDTO(99, "account_99", "type_99", "99.99");
        when(tradeService.getById(99)).thenReturn(trade99);
        when(tradeMapper.toTradeDTO(trade99)).thenReturn(tradeDTO99);
        //when
        mockMvc.perform(get("/trade/update/99"))
                //then
                .andDo(print())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("tradeDTO"))
                .andExpect(model().attribute("tradeDTO", tradeDTO99))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_update_valid_trade_successfully() throws Exception {
        //given
        TradeDTO initialTradeDTO99 = new TradeDTO(99, "account_99", "type_99", "99.99");
        Trade trade99 = new Trade(99, "account_9009", "type_9009", 9009.9009);
        //TradeDTO tradeDTO99 = new TradeDTO(99, "account_9009", "type_9009", "9009.9009");
        when(tradeMapper.toTrade(any(TradeDTO.class))).thenReturn(trade99);
        doNothing().when(tradeService).update(trade99);
        ObjectMapper objectMapper = new ObjectMapper();
        //when
        mockMvc.perform(post("/trade/update/99").with(csrf())
                        .content(objectMapper.writeValueAsBytes(initialTradeDTO99))
                        .param("account", "account_9009")
                        .param("type", "type_9009")
                        .param("buyQuantity", "9009.9009"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
        verify(tradeService).update(trade99);
        assertNotNull(trade99.getRevisionDate());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_delete_trade_successfully() throws Exception {
        //given
        doNothing().when(tradeService).deleteById(1);
        //when
        mockMvc.perform(get("/trade/delete/1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
        verify(tradeService).deleteById(1);
    }

    @Test
    public void should_get_tradeDTOs_successfully() throws Exception {
        //given
        Trade trade1 = new Trade(1, "account_1", "type_1", 1.1);
        Trade trade2 = new Trade(2, "account_2", "type_2", 2.2);
        List<Trade> trades = new ArrayList<>();
        trades.add(trade1);
        trades.add(trade2);
        TradeDTO tradeDTO1 = new TradeDTO(1, "account_1", "type_1", "1.1");
        TradeDTO tradeDTO2 = new TradeDTO(2, "account_2", "type_2", "2.2");
        List<TradeDTO> expectedTradeDTOs = new ArrayList<>();
        expectedTradeDTOs.add(tradeDTO1);
        expectedTradeDTOs.add(tradeDTO2);
        when(tradeService.getTrades()).thenReturn(trades);
        when(tradeMapper.toTradeDTO(trade1)).thenReturn(tradeDTO1);
        when(tradeMapper.toTradeDTO(trade2)).thenReturn(tradeDTO2);
        //when
        List<TradeDTO> actualTradeDTOs = tradeController.getTradeDTOs();
        //then
        assertEquals(expectedTradeDTOs, actualTradeDTOs);
    }

}
