package com.poseidoncapitalsolutions.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidoncapitalsolutions.poseidon.dto.RatingDTO;
import com.poseidoncapitalsolutions.poseidon.service.RatingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/rating")
@Tag(name = "Ratings", description = "Manage Moody's, S&P and Fitch credit ratings")
public class RatingController {
    
    private final RatingService ratingService;
    
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    
    @Operation(summary = "List ratings", description = "Renders the rating view with all existing entries")
    @ApiResponse(responseCode = "200", description = "Rating list view rendered")
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }
    
    @Operation(summary = "Show add form", description = "Renders an empty form for creating a new rating")
    @ApiResponse(responseCode = "200", description = "Add form rendered")
    @GetMapping("/add")
    public String addRatingForm(RatingDTO rating) {
        return "rating/add";
    }
    
    @Operation(summary = "Create a rating", description = "Validates and persists a new rating")
    @ApiResponse(responseCode = "302", description = "Rating saved, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, add form redisplayed with errors")
    @PostMapping("/validate")
    public String validate(@Valid RatingDTO rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.save(rating);
        return "redirect:/rating/list";
    }
    
    @Operation(summary = "Show update form", description = "Loads a single rating for editing by its id")
    @ApiResponse(responseCode = "200", description = "Update form rendered")
    @ApiResponse(responseCode = "404", description = "No rating exists for the given id")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@Parameter(description = "Id of the rating to edit") @PathVariable("id") Integer id, Model model) {
        RatingDTO rating = ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }
    
    @Operation(summary = "Update a rating", description = "Validates and persists changes to an existing rating")
    @ApiResponse(responseCode = "302", description = "Rating updated, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, update form redisplayed with errors")
    @PostMapping("/update/{id}")
    public String updateRating(@Parameter(description = "Id of the rating to update") @PathVariable("id") Integer id, @Valid RatingDTO rating,
                               BindingResult result, Model model) {
        rating.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("rating", rating);
            return "rating/update";
        }
        ratingService.save(rating);
        return "redirect:/rating/list";
    }
    
    @Operation(summary = "Delete a rating", description = "Removes a rating by its id")
    @ApiResponse(responseCode = "302", description = "Rating deleted, redirected to the list view")
    @ApiResponse(responseCode = "404", description = "No rating exists for the given id")
    @GetMapping("/delete/{id}")
    public String deleteRating(@Parameter(description = "Id of the rating to delete") @PathVariable("id") Integer id, Model model) {
        ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingService.deleteById(id);
        return "redirect:/rating/list";
    }
}
