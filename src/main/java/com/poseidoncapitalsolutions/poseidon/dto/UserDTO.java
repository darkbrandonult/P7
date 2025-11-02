package com.poseidoncapitalsolutions.poseidon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for User entity.
 * Used for transferring user data between layers with validation.
 */
@Data
public class UserDTO {
    
    private Integer id;
    
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    @NotBlank(message = "Password is mandatory")
    private String password;
    
    @NotBlank(message = "Full name is mandatory")
    private String fullname;
    
    @NotBlank(message = "Role is mandatory")
    private String role;
}
