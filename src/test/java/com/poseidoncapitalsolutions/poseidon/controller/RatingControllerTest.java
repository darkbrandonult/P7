package com.poseidoncapitalsolutions.poseidon.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.poseidoncapitalsolutions.poseidon.dto.RatingDTO;
import com.poseidoncapitalsolutions.poseidon.service.RatingService;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    private RatingDTO ratingDTO;
    private List<RatingDTO> ratingDTOs;

    @BeforeEach
    void setUp() {
        ratingDTO = new RatingDTO();
        ratingDTO.setId(1);
        ratingDTO.setMoodysRating("A");
        ratingDTO.setSandPRating("A+");
        ratingDTO.setFitchRating("A-");
        ratingDTO.setOrderNumber(1);

        ratingDTOs = new ArrayList<>();
        ratingDTOs.add(ratingDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnRatingListPage() throws Exception {
        when(ratingService.findAll()).thenReturn(ratingDTOs);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", ratingDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addRatingFormShouldReturnAddPage() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToRatingListWhenNoErrors() throws Exception {
        when(ratingService.save(any(RatingDTO.class))).thenReturn(ratingDTO);

        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "A")
                .param("sandPRating", "A+")
                .param("fitchRating", "A-")
                .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(any(RatingDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "")
                .param("sandPRating", "")
                .param("fitchRating", "")
                .param("orderNumber", "-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        when(ratingService.findById(anyInt())).thenReturn(Optional.of(ratingDTO));

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateRatingShouldRedirectToRatingListWhenNoErrors() throws Exception {
        when(ratingService.save(any(RatingDTO.class))).thenReturn(ratingDTO);

        mockMvc.perform(post("/rating/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "A")
                .param("sandPRating", "A+")
                .param("fitchRating", "A-")
                .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(any(RatingDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteRatingShouldRedirectToRatingList() throws Exception {
        when(ratingService.findById(anyInt())).thenReturn(Optional.of(ratingDTO));
        doNothing().when(ratingService).deleteById(anyInt());

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).deleteById(anyInt());
    }
}