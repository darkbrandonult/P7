package com.poseidoncapitalsolutions.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller responsible for handling the home page request.
 * Provides an endpoint for displaying the home page of the application.
 */

@Controller
@Tag(name = "Home", description = "Landing pages after authentication")
public class HomeController {

    @Operation(summary = "Show home page", description = "Renders the authenticated user's landing page")
    @ApiResponse(responseCode = "200", description = "Home page rendered")
    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @Operation(summary = "Redirect admin home", description = "Sends ADMIN users straight to the bid list")
    @ApiResponse(responseCode = "302", description = "Redirected to the bid list view")
    @RequestMapping("/admin/home")
    public String adminHome() {
        return "redirect:/bidList/list";
    }
}