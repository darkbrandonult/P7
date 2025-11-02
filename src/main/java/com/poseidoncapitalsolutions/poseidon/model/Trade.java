package com.poseidoncapitalsolutions.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Entity class representing a Trade.
 * Stores information about trading transactions.
 */
@Entity
@Table(name = "trade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer tradeId;
    
    @NotBlank(message = "Account is mandatory")
    @Column(nullable = false)
    private String account;
    
    @NotBlank(message = "Type is mandatory")
    @Column(nullable = false)
    private String type;
    
    @NotNull(message = "Buy quantity is mandatory")
    @Column(nullable = false)
    private Double buyQuantity;
    
    private Double sellQuantity;
    
    private Double buyPrice;
    
    private Double sellPrice;
    
    private String benchmark;
    
    @Column(name = "tradeDate")
    private Timestamp tradeDate;
    
    private String security;
    
    private String status;
    
    private String trader;
    
    private String book;
    
    private String creationName;
    
    @Column(name = "creationDate")
    private Timestamp creationDate;
    
    private String revisionName;
    
    @Column(name = "revisionDate")
    private Timestamp revisionDate;
    
    private String dealName;
    
    private String dealType;
    
    private String sourceListId;
    
    private String side;
}
