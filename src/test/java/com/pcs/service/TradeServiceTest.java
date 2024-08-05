package com.pcs.service;

import com.pcs.model.Trade;
import com.pcs.repository.TradeRepository;
import com.pcs.web.dto.TradeDTO;
import com.pcs.web.mapper.TradeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private TradeMapper tradeMapper;
    @InjectMocks
    private TradeService tradeService;

    @Test
    public void should_throw_an_exception_when_getting_trade_corresponding_to_id_is_not_found() {
        //given
        Optional<Trade> emptyTrade = Optional.empty();
        when(tradeRepository.findById(anyInt())).thenReturn(emptyTrade);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    tradeService.getById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid trade id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_updating_trade_corresponding_to_id_is_not_found() {
        //given
        Trade testTrade = new Trade();
        testTrade.setTradeId(55);
        Optional<Trade> emptyTrade = Optional.empty();
        when(tradeRepository.findById(55)).thenReturn(emptyTrade);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    tradeService.update(testTrade);
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid trade id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_deleting_trade_corresponding_to_id_is_not_found() {
        //given
        Optional<Trade> emptyTrade = Optional.empty();
        when(tradeRepository.findById(anyInt())).thenReturn(emptyTrade);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    tradeService.deleteById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid trade id", thrown.getMessage());
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
        List<TradeDTO> actualTradeDTOs = tradeService.getTradeDTOs();
        //then
        assertEquals(expectedTradeDTOs, actualTradeDTOs);
    }

}
