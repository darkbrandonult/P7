package com.poseidoncapitalsolutions.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a User in the system.
 * Users have authentication credentials and role-based access control.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Username is mandatory")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "Full name is mandatory")
    @Column(nullable = false)
    private String fullname;
    
    @NotBlank(message = "Role is mandatory")
    @Column(nullable = false)
    private String role;
}
