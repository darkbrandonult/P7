package com.poseidoncapitalsolutions.poseidon.mapper;

import com.poseidoncapitalsolutions.poseidon.dto.UserDTO;
import com.poseidoncapitalsolutions.poseidon.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper interface for User entity and UserDTO conversion.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    
    /**
     * Converts User entity to UserDTO.
     * @param user the user entity
     * @return the user DTO
     */
    UserDTO toDto(User user);
    
    /**
     * Converts UserDTO to User entity.
     * @param userDTO the user DTO
     * @return the user entity
     */
    User toEntity(UserDTO userDTO);
}
