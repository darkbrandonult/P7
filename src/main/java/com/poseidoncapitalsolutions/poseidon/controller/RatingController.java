package com.poseidoncapitalsolutions.poseidon.controller;

import com.poseidoncapitalsolutions.poseidon.dto.RatingDTO;
import com.poseidoncapitalsolutions.poseidon.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rating")
public class RatingController {
    
    private final RatingService ratingService;
    
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    
    /**
     * Display list of all ratings
     */
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }
    
    /**
     * Display add rating form
     */
    @GetMapping("/add")
    public String addRatingForm(RatingDTO rating) {
        return "rating/add";
    }
    
    /**
     * Validate and save new rating
     */
    @PostMapping("/validate")
    public String validate(@Valid RatingDTO rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.save(rating);
        return "redirect:/rating/list";
    }
    
    /**
     * Display update rating form
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingDTO rating = ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }
    
    /**
     * Validate and update rating
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            rating.setId(id);
            return "rating/update";
        }
        rating.setId(id);
        ratingService.save(rating);
        return "redirect:/rating/list";
    }
    
    /**
     * Delete a rating
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingService.deleteById(id);
        return "redirect:/rating/list";
    }
}
