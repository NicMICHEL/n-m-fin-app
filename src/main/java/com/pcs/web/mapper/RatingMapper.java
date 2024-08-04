package com.pcs.web.mapper;

import com.pcs.model.Rating;
import com.pcs.web.dto.RatingDTO;
import org.springframework.stereotype.Controller;

@Controller
public class RatingMapper {

    public RatingDTO toRatingDTO(Rating rating) {
        return new RatingDTO(
                rating.getId(),
                rating.getMoodysRating(),
                rating.getSandPRating(),
                rating.getFitchRating(),
                Integer.toString(rating.getOrderNumber())
        );
    }

    public Rating toRating(RatingDTO ratingDTO) {
        return new Rating(
                ratingDTO.getId(),
                ratingDTO.getMoodysRating(),
                ratingDTO.getSandPRating(),
                ratingDTO.getFitchRating(),
                Integer.parseInt(ratingDTO.getOrderNumber())
        );
    }

}
