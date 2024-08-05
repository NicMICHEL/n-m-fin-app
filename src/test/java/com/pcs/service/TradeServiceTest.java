package com.pcs.service;

import com.pcs.model.Trade;
import com.pcs.repository.TradeRepository;
import com.pcs.web.mapper.TradeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
