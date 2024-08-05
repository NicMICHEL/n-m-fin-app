package com.pcs.service;

import com.pcs.model.BidList;
import com.pcs.repository.BidListRepository;
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
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;
    @InjectMocks
    private BidListService bidListService;

    @Test
    public void should_throw_an_exception_when_getting_bidList_corresponding_to_id_is_not_found() {
        //given
        Optional<BidList> emptyBidList = Optional.empty();
        when(bidListRepository.findById(anyInt())).thenReturn(emptyBidList);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    bidListService.getById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid bidList id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_updating_bidList_corresponding_to_id_is_not_found() {
        //given
        BidList testBidList = new BidList();
        testBidList.setBidListId(55);
        Optional<BidList> emptyBidList = Optional.empty();
        when(bidListRepository.findById(55)).thenReturn(emptyBidList);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    bidListService.update(testBidList);
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid bidList id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_deleting_bidList_corresponding_to_id_is_not_found() {
        //given
        Optional<BidList> emptyBidList = Optional.empty();
        when(bidListRepository.findById(anyInt())).thenReturn(emptyBidList);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    bidListService.deleteById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid bidList id", thrown.getMessage());
    }

}
