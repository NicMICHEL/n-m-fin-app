package com.pcs.service;

import com.pcs.model.Rating;
import com.pcs.repository.RatingRepository;
import com.pcs.web.dto.RatingDTO;
import com.pcs.web.mapper.RatingMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private RatingMapper ratingMapper;

    private static final Logger logger = LogManager.getLogger(RatingService.class);

    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }


    public Rating getById(Integer id) throws IllegalArgumentException {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            return rating.get();
        } else {
            logger.error("Unable to find rating corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid rating id");
        }
    }

    @Transactional
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @Transactional
    public void update(Rating rating) throws IllegalArgumentException {
        Optional<Rating> testRating = ratingRepository.findById(rating.getId());
        if (testRating.isPresent()) {
            ratingRepository.save(rating);
        } else {
            logger.error("Unable to find and update rating corresponding to id {}", rating.getId());
            throw new IllegalArgumentException("Invalid rating id");
        }
    }

    public void deleteById(Integer id) throws IllegalArgumentException {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            ratingRepository.deleteById(id);
        } else {
            logger.error("Unable to find and delete Rating corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid rating id");
        }
    }

    public List<RatingDTO> getRatingDTOs() {
        List<Rating> ratings = getRatings();
        List<RatingDTO> ratingDTOs = new ArrayList<>();
        ratings.forEach(rating -> {
            ratingDTOs.add(ratingMapper.toRatingDTO(rating));
        });
        return ratingDTOs;
    }

}
