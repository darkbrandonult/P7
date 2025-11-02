package com.poseidoncapitalsolutions.poseidon.repository;

import com.poseidoncapitalsolutions.poseidon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 * Extends JpaRepository for basic CRUD operations and JpaSpecificationExecutor for advanced queries.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    
    /**
     * Finds a user by username.
     * @param username the username to search for
     * @return the user with the given username, or null if not found
     */
    User findByUsername(String username);
}
