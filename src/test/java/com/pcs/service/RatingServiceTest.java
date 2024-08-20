package com.pcs.service;

import com.pcs.model.Rating;
import com.pcs.repository.RatingRepository;
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
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;
    @InjectMocks
    private RatingService ratingService;

    @Test
    public void should_throw_an_exception_when_getting_rating_corresponding_to_id_is_not_found() {
        //given
        Optional<Rating> emptyRating = Optional.empty();
        when(ratingRepository.findById(anyInt())).thenReturn(emptyRating);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    ratingService.getById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid rating id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_updating_rating_corresponding_to_id_is_not_found() {
        //given
        Rating testRating = new Rating();
        testRating.setId(55);
        Optional<Rating> emptyRating = Optional.empty();
        when(ratingRepository.findById(55)).thenReturn(emptyRating);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    ratingService.update(testRating);
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid rating id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_deleting_rating_corresponding_to_id_is_not_found() {
        //given
        Optional<Rating> emptyRating = Optional.empty();
        when(ratingRepository.findById(anyInt())).thenReturn(emptyRating);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    ratingService.deleteById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid rating id", thrown.getMessage());
    }

}
