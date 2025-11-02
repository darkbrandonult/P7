package com.poseidoncapitalsolutions.poseidon.mapper;

import com.poseidoncapitalsolutions.poseidon.dto.RatingDTO;
import com.poseidoncapitalsolutions.poseidon.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper interface for Rating entity and RatingDTO conversion.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper {
    
    /**
     * Converts Rating entity to RatingDTO.
     * @param rating the rating entity
     * @return the rating DTO
     */
    RatingDTO toDto(Rating rating);
    
    /**
     * Converts RatingDTO to Rating entity.
     * @param ratingDTO the rating DTO
     * @return the rating entity
     */
    Rating toEntity(RatingDTO ratingDTO);
}
