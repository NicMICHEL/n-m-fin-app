package com.pcs.web.mapper;

import com.pcs.model.BidList;
import com.pcs.service.BidListService;
import com.pcs.web.dto.BidListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BidListMapper {

    @Autowired
    private BidListService bidListService;

    public BidListDTO toBidListDTO(BidList bidList) {
        return new BidListDTO(
                bidList.getBidListId(),
                bidList.getAccount(),
                bidList.getType(),
                Double.toString(bidList.getBidQuantity())
        );
    }

    public BidList toBidList(BidListDTO bidListDTO) {
        //test to avoid setting other attributes to zero
        if (bidListDTO.getBidListId() == null) {
            return new BidList(
                    bidListDTO.getBidListId(),
                    bidListDTO.getAccount(),
                    bidListDTO.getType(),
                    Double.parseDouble(bidListDTO.getBidQuantity())
            );
        } else {
            BidList bidList = bidListService.getById(bidListDTO.getBidListId());
            bidList.setAccount(bidListDTO.getAccount());
            bidList.setType(bidListDTO.getType());
            bidList.setBidQuantity(Double.parseDouble(bidListDTO.getBidQuantity()));
            return bidList;
        }
    }

}
