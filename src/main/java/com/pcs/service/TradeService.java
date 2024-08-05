package com.pcs.service;

import com.pcs.model.Trade;
import com.pcs.repository.TradeRepository;
import com.pcs.web.mapper.TradeMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    private static final Logger logger = LogManager.getLogger(TradeService.class);

    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }


    public Trade getById(Integer id) throws IllegalArgumentException {
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            return trade.get();
        } else {
            logger.error("Unable to find trade corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid trade id");
        }
    }

    @Transactional
    public void save(Trade trade) {
        tradeRepository.save(trade);
    }

    @Transactional
    public void update(Trade trade) throws IllegalArgumentException {
        Optional<Trade> testTrade = tradeRepository.findById(trade.getTradeId());
        if (testTrade.isPresent()) {
            tradeRepository.save(trade);
        } else {
            logger.error("Unable to find and update trade corresponding to id {}", trade.getTradeId());
            throw new IllegalArgumentException("Invalid trade id");
        }
    }

    public void deleteById(Integer id) throws IllegalArgumentException {
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            tradeRepository.deleteById(id);
        } else {
            logger.error("Unable to find and delete trade corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid trade id");
        }
    }

}
