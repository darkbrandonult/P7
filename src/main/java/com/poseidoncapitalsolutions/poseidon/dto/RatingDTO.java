package com.poseidoncapitalsolutions.poseidon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Data Transfer Object for Rating entity.
 * Used for transferring rating data between layers with validation.
 */
@Data
public class RatingDTO {
    
    private Integer id;
    
    @NotBlank(message = "Moody's rating cannot be empty")
    private String moodysRating;
    
    @NotBlank(message = "S&P rating cannot be empty")
    private String sandPRating;
    
    @NotBlank(message = "Fitch rating cannot be empty")
    private String fitchRating;
    
    @NotNull(message = "Order number is required")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;
}
