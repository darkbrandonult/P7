package com.poseidoncapitalsolutions.poseidon.repository;

import com.poseidoncapitalsolutions.poseidon.model.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for RuleName entity.
 * Provides CRUD operations for rule names.
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
