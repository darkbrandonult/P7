package com.poseidoncapitalsolutions.poseidon.service;

import com.poseidoncapitalsolutions.poseidon.dto.UserDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.UserMapper;
import com.poseidoncapitalsolutions.poseidon.model.User;
import com.poseidoncapitalsolutions.poseidon.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for User entity operations.
 * Handles business logic for user management including password encoding.
 */
@Service
@Transactional
public class UserService implements GenericService<UserDTO> {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Constructor with dependency injection.
     * @param userRepository the user repository
     * @param userMapper the user mapper
     * @param passwordEncoder the password encoder for securing passwords
     */
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<UserDTO> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }
    
    @Override
    public UserDTO save(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        // Encode password before saving
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    
    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
    
    /**
     * Finds a user by username.
     * @param username the username to search for
     * @return the user DTO if found
     */
    public Optional<UserDTO> findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? Optional.of(userMapper.toDto(user)) : Optional.empty();
    }
}
