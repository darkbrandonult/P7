package com.poseidoncapitalsolutions.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Entity class representing a Bid List.
 * Stores information about bids for financial instruments.
 */
@Entity
@Table(name = "bidlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Integer bidListId;
    
    @NotBlank(message = "Account is mandatory")
    @Column(nullable = false)
    private String account;
    
    @NotBlank(message = "Type is mandatory")
    @Column(nullable = false)
    private String type;
    
    @NotNull(message = "Bid quantity is mandatory")
    @Column(nullable = false)
    private Double bidQuantity;
    
    private Double askQuantity;
    
    private Double bid;
    
    private Double ask;
    
    private String benchmark;
    
    @Column(name = "bidListDate")
    private Timestamp bidListDate;
    
    private String commentary;
    
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
