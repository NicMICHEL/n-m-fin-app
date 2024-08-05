package com.pcs.web.mapper;

import com.pcs.model.Trade;
import com.pcs.repository.TradeRepository;
import com.pcs.service.TradeService;
import com.pcs.web.dto.TradeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TradeMapperTest {

    @Mock
    private TradeRepository tradeRepository;
    @InjectMocks
    private TradeMapper tradeMapper;
    @Mock
    private TradeService tradeService;


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
        //when
        List<TradeDTO> actualTradeDTOs = tradeMapper.getTradeDTOs();
        //then
        assertEquals(expectedTradeDTOs, actualTradeDTOs);
    }

}
