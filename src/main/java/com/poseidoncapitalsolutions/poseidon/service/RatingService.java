package com.poseidoncapitalsolutions.poseidon.service;

import com.poseidoncapitalsolutions.poseidon.dto.RatingDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.RatingMapper;
import com.poseidoncapitalsolutions.poseidon.model.Rating;
import com.poseidoncapitalsolutions.poseidon.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Rating entity operations.
 * Handles business logic and coordinates between controller and repository layers.
 */
@Service
@Transactional
public class RatingService implements GenericService<RatingDTO> {
    
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    
    /**
     * Constructor with dependency injection.
     * @param ratingRepository the rating repository
     * @param ratingMapper the rating mapper
     */
    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }
    
    @Override
    public List<RatingDTO> findAll() {
        return ratingRepository.findAll().stream()
                .map(ratingMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<RatingDTO> findById(Integer id) {
        return ratingRepository.findById(id)
                .map(ratingMapper::toDto);
    }
    
    @Override
    public RatingDTO save(RatingDTO dto) {
        Rating rating = ratingMapper.toEntity(dto);
        Rating savedRating = ratingRepository.save(rating);
        return ratingMapper.toDto(savedRating);
    }
    
    @Override
    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
}
