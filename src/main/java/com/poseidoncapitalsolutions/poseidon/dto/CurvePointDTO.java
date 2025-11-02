package com.poseidoncapitalsolutions.poseidon.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Data Transfer Object for CurvePoint entity.
 * Used for transferring curve point data between layers with validation.
 */
@Data
public class CurvePointDTO {
    
    private Integer id;
    
    @NotNull(message = "Curve ID is mandatory")
    @Positive(message = "Curve ID must be positive")
    private Integer curveId;
    
    private Timestamp asOfDate;
    
    @NotNull(message = "Term is mandatory")
    private Double term;
    
    @NotNull(message = "Value is mandatory")
    private Double value;
    
    private Timestamp creationDate;
}
