package com.poseidoncapitalsolutions.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Rating.
 * Stores credit ratings from various rating agencies.
 */
@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Moody's rating cannot be empty")
    @Column(nullable = false)
    private String moodysRating;
    
    @NotBlank(message = "S&P rating cannot be empty")
    @Column(nullable = false)
    private String sandPRating;
    
    @NotBlank(message = "Fitch rating cannot be empty")
    @Column(nullable = false)
    private String fitchRating;
    
    @NotNull(message = "Order number is required")
    @Positive(message = "Order number must be positive")
    @Column(nullable = false)
    private Integer orderNumber;
}
