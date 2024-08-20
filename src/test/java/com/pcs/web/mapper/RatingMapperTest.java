package com.pcs.web.mapper;

import com.pcs.model.Rating;
import com.pcs.service.RatingService;
import com.pcs.web.dto.RatingDTO;
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
public class RatingMapperTest {

    @InjectMocks
    private RatingMapper ratingMapper;
    @Mock
    private RatingService ratingService;


    @Test
    public void should_get_ratingDTOs_successfully() throws Exception {
        //given
        Rating rating1 = new Rating(1, "MoodysRating_1", "SandPRating_1",
                "FitchRating_1", 11);
        Rating rating2 = new Rating(2, "MoodysRating_2", "SandPRating_2",
                "FitchRating_2", 22);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating1);
        ratings.add(rating2);
        RatingDTO ratingDTO1 = new RatingDTO(1, "MoodysRating_1", "SandPRating_1",
                "FitchRating_1", "11");
        RatingDTO ratingDTO2 = new RatingDTO(2, "MoodysRating_2", "SandPRating_2",
                "FitchRating_2", "22");
        List<RatingDTO> expectedRatingDTOs = new ArrayList<>();
        expectedRatingDTOs.add(ratingDTO1);
        expectedRatingDTOs.add(ratingDTO2);
        when(ratingService.getRatings()).thenReturn(ratings);
        //when
        List<RatingDTO> actualRatingDTOs = ratingMapper.getRatingDTOs();
        //then
        assertEquals(expectedRatingDTOs, actualRatingDTOs);
    }

}
