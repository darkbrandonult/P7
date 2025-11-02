package com.poseidoncapitalsolutions.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Rule Name.
 * Stores business rules and their associated SQL components.
 */
@Entity
@Table(name = "rulename")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleName {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Description is mandatory")
    @Column(nullable = false)
    private String description;
    
    private String json;
    
    private String template;
    
    private String sqlStr;
    
    private String sqlPart;
}
