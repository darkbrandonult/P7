package com.poseidoncapitalsolutions.poseidon.repository;

import com.poseidoncapitalsolutions.poseidon.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Rating entity.
 * Provides CRUD operations for ratings.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
