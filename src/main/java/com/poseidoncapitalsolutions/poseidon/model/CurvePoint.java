package com.poseidoncapitalsolutions.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Entity class representing a Curve Point.
 * Stores information about points on a financial curve.
 */
@Entity
@Table(name = "curvepoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurvePoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "Curve ID is mandatory")
    @Column(nullable = false)
    private Integer curveId;
    
    @Column(name = "asOfDate")
    private Timestamp asOfDate;
    
    @NotNull(message = "Term is mandatory")
    @Column(nullable = false)
    private Double term;
    
    @NotNull(message = "Value is mandatory")
    @Column(name = "`value`", nullable = false)
    private Double value;
    
    @Column(name = "creationDate")
    private Timestamp creationDate;
}
