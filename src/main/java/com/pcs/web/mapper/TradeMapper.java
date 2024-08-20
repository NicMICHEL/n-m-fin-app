package com.pcs.web.mapper;

import com.pcs.model.Trade;
import com.pcs.service.TradeService;
import com.pcs.web.dto.TradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TradeMapper {

    @Autowired
    TradeService tradeService;

    public TradeDTO toTradeDTO(Trade trade) {
        return new TradeDTO(
                trade.getTradeId(),
                trade.getAccount(),
                trade.getType(),
                Double.toString(trade.getBuyQuantity())
        );
    }

    public Trade toTrade(TradeDTO tradeDTO) {
        //test to avoid setting other attributes to zero
        if (tradeDTO.getTradeId() == null) {
            return new Trade(
                    tradeDTO.getAccount(),
                    tradeDTO.getType(),
                    Double.parseDouble(tradeDTO.getBuyQuantity()));
        } else {
            Trade trade = tradeService.getById(tradeDTO.getTradeId());
            trade.setAccount(tradeDTO.getAccount());
            trade.setType(tradeDTO.getType());
            trade.setBuyQuantity(Double.parseDouble(tradeDTO.getBuyQuantity()));
            return trade;
        }
    }

    public List<TradeDTO> getTradeDTOs() {
        List<Trade> trades = tradeService.getTrades();
        List<TradeDTO> tradeDTOs = new ArrayList<>();
        trades.forEach(trade -> {
            tradeDTOs.add(toTradeDTO(trade));
        });
        return tradeDTOs;
    }

}
