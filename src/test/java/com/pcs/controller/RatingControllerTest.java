package com.pcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.model.Rating;
import com.pcs.service.RatingService;
import com.pcs.web.dto.RatingDTO;
import com.pcs.web.mapper.RatingMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @MockBean
    private RatingService ratingService;
    @MockBean
    private RatingMapper ratingMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user")
    public void should_return_to_rating_List_page_successfully() throws Exception {
        //given
        RatingDTO ratingDTO1 = new RatingDTO(1, "MoodysRating_1", "SandPRating_1",
                "FitchRating_1", "11");
        RatingDTO ratingDTO2 = new RatingDTO(2, "MoodysRating_2", "SandPRating_2",
                "FitchRating_2", "22");
        List<RatingDTO> expectedRatingDTOs = new ArrayList<>();
        expectedRatingDTOs.add(ratingDTO1);
        expectedRatingDTOs.add(ratingDTO2);
        when(ratingMapper.getRatingDTOs()).thenReturn(expectedRatingDTOs);
        //when
        mockMvc.perform(get("/rating/list"))
                //then
                .andDo(print())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratingDTOs"))
                .andExpect(model().attribute("ratingDTOs", expectedRatingDTOs))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_rating_add_page_successfully() throws Exception {
        //given
        RatingDTO expectedRatingDTO = new RatingDTO();
        //when
        mockMvc.perform(get("/rating/add"))
                //then
                .andDo(print())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(model().attribute("ratingDTO", expectedRatingDTO))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_save_valid_rating_successfully() throws Exception {
        //given
        RatingDTO ratingDTO1 = new RatingDTO(null, "MoodysRating_1", "SandPRating_1",
                "FitchRating_1", "11");
        Rating rating1 = new Rating(null, "MoodysRating_1", "SandPRating_1",
                "FitchRating_1", 11);
        when(ratingMapper.toRating(ratingDTO1)).thenReturn(rating1);
        doNothing().when(ratingService).save(rating1);
        ObjectMapper objectMapper = new ObjectMapper();
        RatingDTO ratingDTO = new RatingDTO();
        //when
        mockMvc.perform(post("/rating/validate").with(csrf())
                        .content(objectMapper.writeValueAsBytes(ratingDTO))
                        .param("moodysRating", "MoodysRating_1")
                        .param("sandPRating", "SandPRating_1")
                        .param("fitchRating", "FitchRating_1")
                        .param("orderNumber", "11"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
        verify(ratingService).save(rating1);
    }

    @Test
    @WithMockUser(username = "user")
    public void should_return_rating_update_page_successfully() throws Exception {
        //given
        Rating rating99 = new Rating(99, "MoodysRating_99", "SandPRating_99",
                "FitchRating_99", 99);
        RatingDTO ratingDTO99 = new RatingDTO(99, "MoodysRating_99", "SandPRating_99",
                "FitchRating_99", "99");
        when(ratingService.getById(99)).thenReturn(rating99);
        when(ratingMapper.toRatingDTO(rating99)).thenReturn(ratingDTO99);
        //when
        mockMvc.perform(get("/rating/update/99"))
                //then
                .andDo(print())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(model().attribute("ratingDTO", ratingDTO99))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user")
    public void should_update_valid_rating_successfully() throws Exception {
        //given
        RatingDTO initialRatingDTO99 = new RatingDTO(99, "MoodysRating_99", "SandPRating_99",
                "FitchRating_99", "99");
        Rating rating99 = new Rating(99, "MoodysRating_9009", "SandPRating_9009",
                "FitchRating_9009", 9009);
        when(ratingMapper.toRating(any(RatingDTO.class))).thenReturn(rating99);
        doNothing().when(ratingService).update(rating99);
        ObjectMapper objectMapper = new ObjectMapper();
        //when
        mockMvc.perform(post("/rating/update/99").with(csrf())
                        .content(objectMapper.writeValueAsBytes(initialRatingDTO99))
                        .param("moodysRating", "MoodysRating_9009")
                        .param("sandPRating", "SandPRating_9009")
                        .param("fitchRating", "FitchRating_9009")
                        .param("orderNumber", "9009"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
        verify(ratingService).update(rating99);
    }

    @Test
    @WithMockUser(username = "user")
    public void should_delete_rating_successfully() throws Exception {
        //given
        doNothing().when(ratingService).deleteById(1);
        //when
        mockMvc.perform(get("/rating/delete/1"))
                //then
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
        verify(ratingService).deleteById(1);
    }

}
