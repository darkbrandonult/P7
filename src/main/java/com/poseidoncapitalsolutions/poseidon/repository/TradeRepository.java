package com.poseidoncapitalsolutions.poseidon.repository;

import com.poseidoncapitalsolutions.poseidon.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Trade entity.
 * Provides CRUD operations for trades.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
