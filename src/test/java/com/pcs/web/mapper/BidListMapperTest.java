package com.pcs.web.mapper;

import com.pcs.model.BidList;
import com.pcs.repository.BidListRepository;
import com.pcs.service.BidListService;
import com.pcs.web.dto.BidListDTO;
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
public class BidListMapperTest {

    @Mock
    private BidListRepository bidListRepository;
    @InjectMocks
    private BidListMapper bidListMapper;
    @Mock
    private BidListService bidListService;


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
        //when
        List<BidListDTO> actualBidListDTOs = bidListMapper.getBidListDTOs();
        //then
        assertEquals(expectedBidListDTOs, actualBidListDTOs);
    }

}
