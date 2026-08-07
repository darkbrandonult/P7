package com.poseidoncapitalsolutions.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidoncapitalsolutions.poseidon.dto.BidListDTO;
import com.poseidoncapitalsolutions.poseidon.service.BidListService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/bidList")
@Tag(name = "Bid List", description = "Manage bid list entries for financial instruments")
public class BidListController {
    
    private final BidListService bidListService;
    
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }
    
    @Operation(summary = "List bids", description = "Renders the bid list view with all existing bid entries")
    @ApiResponse(responseCode = "200", description = "Bid list view rendered")
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }
    
    @Operation(summary = "Show add form", description = "Renders an empty form for creating a new bid")
    @ApiResponse(responseCode = "200", description = "Add form rendered")
    @GetMapping("/add")
    public String addBidForm(BidListDTO bid) {
        return "bidList/add";
    }
    
    @Operation(summary = "Create a bid", description = "Validates and persists a new bid list entry")
    @ApiResponse(responseCode = "302", description = "Bid saved, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, add form redisplayed with errors")
    @PostMapping("/validate")
    public String validate(@Valid BidListDTO bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.save(bid);
        return "redirect:/bidList/list";
    }
    
    @Operation(summary = "Show update form", description = "Loads a single bid for editing by its id")
    @ApiResponse(responseCode = "200", description = "Update form rendered")
    @ApiResponse(responseCode = "404", description = "No bid exists for the given id")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@Parameter(description = "Id of the bid to edit") @PathVariable("id") Integer id, Model model) {
        BidListDTO bid = bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }
    
    @Operation(summary = "Update a bid", description = "Validates and persists changes to an existing bid")
    @ApiResponse(responseCode = "302", description = "Bid updated, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, update form redisplayed with errors")
    @PostMapping("/update/{id}")
    public String updateBid(@Parameter(description = "Id of the bid to update") @PathVariable("id") Integer id, @Valid BidListDTO bid,
                            BindingResult result, Model model) {
        bid.setBidListId(id);
        if (result.hasErrors()) {
            model.addAttribute("bidList", bid);
            return "bidList/update";
        }
        bidListService.save(bid);
        return "redirect:/bidList/list";
    }
    
    @Operation(summary = "Delete a bid", description = "Removes a bid list entry by its id")
    @ApiResponse(responseCode = "302", description = "Bid deleted, redirected to the list view")
    @ApiResponse(responseCode = "404", description = "No bid exists for the given id")
    @GetMapping("/delete/{id}")
    public String deleteBid(@Parameter(description = "Id of the bid to delete") @PathVariable("id") Integer id, Model model) {
        bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        bidListService.deleteById(id);
        return "redirect:/bidList/list";
    }
}
