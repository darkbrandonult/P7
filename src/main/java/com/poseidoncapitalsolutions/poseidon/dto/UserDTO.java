package com.poseidoncapitalsolutions.poseidon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "The password must contain at least 8 characters, one uppercase letter, one number, and one symbol."
    )
    private String password;
    
    @NotBlank(message = "Full name is mandatory")
    private String fullname;
    
    @NotBlank(message = "Role is mandatory")
    private String role;
}
