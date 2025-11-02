package com.poseidoncapitalsolutions.poseidon.repository;

import com.poseidoncapitalsolutions.poseidon.model.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for BidList entity.
 * Provides CRUD operations for bid lists.
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {
}
