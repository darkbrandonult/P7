package com.poseidoncapitalsolutions.poseidon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Data Transfer Object for BidList entity.
 * Used for transferring bid list data between layers with validation.
 */
@Data
public class BidListDTO {
    
    private Integer bidListId;
    
    @NotBlank(message = "Account is mandatory")
    private String account;
    
    @NotBlank(message = "Type is mandatory")
    private String type;
    
    @NotNull(message = "Bid quantity is mandatory")
    @Positive(message = "Bid quantity must be positive")
    private Double bidQuantity;
    
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
