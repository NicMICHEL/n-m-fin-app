package com.pcs.service;

import com.pcs.model.BidList;
import com.pcs.repository.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    private static final Logger logger = LogManager.getLogger(BidListService.class);

    public List<BidList> getBidLists() {
        return bidListRepository.findAll();
    }


    public BidList getById(Integer id) throws IllegalArgumentException {
        Optional<BidList> bidList = bidListRepository.findById(id);
        if (bidList.isPresent()) {
            return bidList.get();
        } else {
            logger.error("Unable to find bidList corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid bidList id");
        }
    }

    @Transactional
    public void save(BidList bidList) {
        bidListRepository.save(bidList);
    }

    @Transactional
    public void update(BidList bidList) throws IllegalArgumentException {
        Optional<BidList> testBidList = bidListRepository.findById(bidList.getBidListId());
        if (testBidList.isPresent()) {
            bidListRepository.save(bidList);
        } else {
            logger.error("Unable to find and update bidList corresponding to id {}", bidList.getBidListId());
            throw new IllegalArgumentException("Invalid bidList id");
        }
    }

    public void deleteById(Integer id) throws IllegalArgumentException {
        Optional<BidList> bidList = bidListRepository.findById(id);
        if (bidList.isPresent()) {
            bidListRepository.deleteById(id);
        } else {
            logger.error("Unable to find and delete bidList corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid bidList id");
        }
    }

}
