package com.poseidoncapitalsolutions.poseidon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for RuleName entity.
 * Used for transferring rule name data between layers with validation.
 */
@Data
public class RuleNameDTO {
    
    private Integer id;
    
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    @NotBlank(message = "Description is mandatory")
    private String description;
    
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
}
