package com.pcs.web.mapper;

import com.pcs.model.Rating;
import com.pcs.service.RatingService;
import com.pcs.web.dto.RatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RatingMapper {

    @Autowired
    private RatingService ratingService;

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

    public List<RatingDTO> getRatingDTOs() {
        List<Rating> ratings = ratingService.getRatings();
        List<RatingDTO> ratingDTOs = new ArrayList<>();
        ratings.forEach(rating -> {
            ratingDTOs.add(toRatingDTO(rating));
        });
        return ratingDTOs;
    }

}
