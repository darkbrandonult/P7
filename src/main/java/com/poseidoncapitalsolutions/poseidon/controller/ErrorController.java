package com.poseidoncapitalsolutions.poseidon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for handling error pages.
 * Provides endpoints for displaying custom error messages for 403 and 404 errors.
 */
@Controller
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    /**
     * Displays a custom 403 error page for unauthorized access.
     * 
     * @param model The model to pass the error message to the view.
     * @return The view name for the 403 error page.
     */
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
    @GetMapping("/404")
    public String error404(Model model) {
        logger.warn("Resource not found (404)");
        return "error/404";
    }
}