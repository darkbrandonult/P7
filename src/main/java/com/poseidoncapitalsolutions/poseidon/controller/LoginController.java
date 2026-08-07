package com.poseidoncapitalsolutions.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.poseidoncapitalsolutions.poseidon.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@Controller
@Tag(name = "Login", description = "Authentication entry points")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "Redirect to login", description = "Sends unauthenticated visitors from the root path to the login page")
    @ApiResponse(responseCode = "302", description = "Redirected to /app/login")
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/app/login";
    }

    @Operation(summary = "Show login page", description = "Renders the login form")
    @ApiResponse(responseCode = "200", description = "Login page rendered")
    @GetMapping("/app/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @Operation(summary = "List users (legacy)", description = "Renders the user list view; kept for backward compatibility")
    @ApiResponse(responseCode = "200", description = "User list view rendered")
    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }
}