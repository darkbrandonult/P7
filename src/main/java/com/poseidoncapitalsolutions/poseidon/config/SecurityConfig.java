package com.poseidoncapitalsolutions.poseidon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration for the Poseidon Trading Platform.
 * Configures authentication, authorization, and session management.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Provides BCrypt password encoder for secure password hashing.
     * 
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures HTTP security including authentication, authorization, and login/logout.
     * 
     * Role-based access control:
     * - /user/** endpoints require ADMIN role
     * - All other endpoints require authentication
     * - Login and error pages are publicly accessible
     * 
     * @param http HttpSecurity configuration object
     * @return configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/app/login", "/error", "/css/**", "/js/**").permitAll()
                .requestMatchers("/user/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/app/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/app/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/app/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/403"));
        
        return http.build();
    }
}
