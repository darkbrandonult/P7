package com.poseidoncapitalsolutions.poseidon.repository;

import com.poseidoncapitalsolutions.poseidon.model.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for CurvePoint entity.
 * Provides CRUD operations for curve points.
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
}
