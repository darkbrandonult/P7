package com.poseidoncapitalsolutions.poseidon.service;

import java.util.List;
import java.util.Optional;

/**
 * Generic service interface defining common CRUD operations.
 * @param <T> the type of DTO this service handles
 */
public interface GenericService<T> {
    
    /**
     * Retrieves all entities.
     * @return list of all entities as DTOs
     */
    List<T> findAll();
    
    /**
     * Finds an entity by ID.
     * @param id the entity ID
     * @return the entity as DTO wrapped in Optional
     */
    Optional<T> findById(Integer id);
    
    /**
     * Saves or updates an entity.
     * @param dto the DTO to save
     * @return the saved entity as DTO
     */
    T save(T dto);
    
    /**
     * Deletes an entity by ID.
     * @param id the entity ID to delete
     */
    void deleteById(Integer id);
}
