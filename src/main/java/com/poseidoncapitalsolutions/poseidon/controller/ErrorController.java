package com.poseidoncapitalsolutions.poseidon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller responsible for handling error pages.
 * Provides endpoints for displaying custom error messages for 403 and 404 errors.
 */
@Controller
@Tag(name = "Errors", description = "Custom error pages")
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    /**
     * Displays a custom 403 error page for unauthorized access.
     * 
     * @param model The model to pass the error message to the view.
     * @return The view name for the 403 error page.
     */
    @Operation(summary = "Show 403 page", description = "Renders the access-denied view for unauthorized requests")
    @ApiResponse(responseCode = "200", description = "403 error page rendered")
    @GetMapping("/403")
    public String error403(Model model) {
        String errorMessage = "You are not authorized for the requested data.";
        logger.warn("Access denied: {}", errorMessage);
        model.addAttribute("errorMsg", errorMessage);
        return "error/403";
    }

    /**
     * Displays the default 404 error page for not found resources.
     * 
     * @param model The model to pass any necessary attributes to the view.
     * @return The view name for the 404 error page.
     */
    @Operation(summary = "Show 404 page", description = "Renders the not-found view for unresolved routes")
    @ApiResponse(responseCode = "200", description = "404 error page rendered")
    @GetMapping("/404")
    public String error404(Model model) {
        logger.warn("Resource not found (404)");
        return "error/404";
    }
}