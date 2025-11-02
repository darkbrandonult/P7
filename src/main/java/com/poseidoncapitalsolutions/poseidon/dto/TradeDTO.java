package com.poseidoncapitalsolutions.poseidon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Data Transfer Object for Trade entity.
 * Used for transferring trade data between layers with validation.
 */
@Data
public class TradeDTO {
    
    private Integer tradeId;
    
    @NotBlank(message = "Account is mandatory")
    private String account;
    
    @NotBlank(message = "Type is mandatory")
    private String type;
    
    @NotNull(message = "Buy quantity is mandatory")
    @Positive(message = "Buy quantity must be positive")
    private Double buyQuantity;
    
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
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
