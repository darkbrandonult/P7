package com.poseidoncapitalsolutions.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidoncapitalsolutions.poseidon.dto.RatingDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.RatingMapper;
import com.poseidoncapitalsolutions.poseidon.model.Rating;
import com.poseidoncapitalsolutions.poseidon.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating;
    private RatingDTO ratingDTO;

    @BeforeEach
    void setUp() {
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("A");
        rating.setSandPRating("A+");
        rating.setFitchRating("A-");
        rating.setOrderNumber(1);

        ratingDTO = new RatingDTO();
        ratingDTO.setId(1);
        ratingDTO.setMoodysRating("A");
        ratingDTO.setSandPRating("A+");
        ratingDTO.setFitchRating("A-");
        ratingDTO.setOrderNumber(1);
    }

    @Test
    void findAllShouldReturnListOfRatingDTOs() {
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating));
        when(ratingMapper.toDto(rating)).thenReturn(ratingDTO);

        List<RatingDTO> result = ratingService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0).getMoodysRating());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnRatingDTOWhenFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingMapper.toDto(rating)).thenReturn(ratingDTO);

        Optional<RatingDTO> result = ratingService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("A", result.get().getMoodysRating());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(ratingRepository.findById(999)).thenReturn(Optional.empty());

        Optional<RatingDTO> result = ratingService.findById(999);

        assertTrue(result.isEmpty());
    }

    @Test
    void saveShouldPersistAndReturnRatingDTO() {
        when(ratingMapper.toEntity(ratingDTO)).thenReturn(rating);
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingMapper.toDto(rating)).thenReturn(ratingDTO);

        RatingDTO result = ratingService.save(ratingDTO);

        assertNotNull(result);
        assertEquals("A", result.getMoodysRating());
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        doNothing().when(ratingRepository).deleteById(anyInt());

        ratingService.deleteById(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }
}