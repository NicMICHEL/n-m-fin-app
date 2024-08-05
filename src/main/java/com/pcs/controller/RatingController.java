package com.pcs.controller;

import com.pcs.model.Rating;
import com.pcs.service.RatingService;
import com.pcs.web.dto.RatingDTO;
import com.pcs.web.mapper.RatingMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;
    @Autowired
    private RatingMapper ratingMapper;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratingDTOs", ratingService.getRatingDTOs());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String showRatingAddForm(RatingDTO ratingDTO) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDTO ratingDTO, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Rating rating = ratingMapper.toRating(ratingDTO);
            ratingService.save(rating);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws IllegalArgumentException {
        model.addAttribute(
                "ratingDTO", ratingMapper.toRatingDTO(ratingService.getById(id)));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO ratingDTO,
                               BindingResult result, Model model) throws IllegalArgumentException {
        if (!result.hasErrors()) {
            Rating rating = ratingMapper.toRating(ratingDTO);
            ratingService.update(rating);
            return "redirect:/rating/list";
        }
        model.addAttribute("ratingDTO", ratingDTO);
        return "rating/update";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) throws IllegalArgumentException {
        ratingService.deleteById(id);
        return "redirect:/rating/list";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String handleIllegalArgumentException
            (IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException.getMessage();
    }

}
