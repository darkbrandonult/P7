package com.poseidoncapitalsolutions.poseidon.controller;

import com.poseidoncapitalsolutions.poseidon.dto.TradeDTO;
import com.poseidoncapitalsolutions.poseidon.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trade")
@Tag(name = "Trades", description = "Manage trade records")
public class TradeController {
    
    private final TradeService tradeService;
    
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }
    
    @Operation(summary = "List trades", description = "Renders the trade view with all existing entries")
    @ApiResponse(responseCode = "200", description = "Trade list view rendered")
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }
    
    @Operation(summary = "Show add form", description = "Renders an empty form for creating a new trade")
    @ApiResponse(responseCode = "200", description = "Add form rendered")
    @GetMapping("/add")
    public String addTradeForm(TradeDTO trade) {
        return "trade/add";
    }
    
    @Operation(summary = "Create a trade", description = "Validates and persists a new trade")
    @ApiResponse(responseCode = "302", description = "Trade saved, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, add form redisplayed with errors")
    @PostMapping("/validate")
    public String validate(@Valid TradeDTO trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.save(trade);
        return "redirect:/trade/list";
    }
    
    @Operation(summary = "Show update form", description = "Loads a single trade for editing by its id")
    @ApiResponse(responseCode = "200", description = "Update form rendered")
    @ApiResponse(responseCode = "404", description = "No trade exists for the given id")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@Parameter(description = "Id of the trade to edit") @PathVariable("id") Integer id, Model model) {
        TradeDTO trade = tradeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }
    
    @Operation(summary = "Update a trade", description = "Validates and persists changes to an existing trade")
    @ApiResponse(responseCode = "302", description = "Trade updated, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, update form redisplayed with errors")
    @PostMapping("/update/{id}")
    public String updateTrade(@Parameter(description = "Id of the trade to update") @PathVariable("id") Integer id, @Valid TradeDTO trade,
                              BindingResult result, Model model) {
        trade.setTradeId(id);
        if (result.hasErrors()) {
            model.addAttribute("trade", trade);
            return "trade/update";
        }
        tradeService.save(trade);
        return "redirect:/trade/list";
    }
    
    @Operation(summary = "Delete a trade", description = "Removes a trade by its id")
    @ApiResponse(responseCode = "302", description = "Trade deleted, redirected to the list view")
    @ApiResponse(responseCode = "404", description = "No trade exists for the given id")
    @GetMapping("/delete/{id}")
    public String deleteTrade(@Parameter(description = "Id of the trade to delete") @PathVariable("id") Integer id, Model model) {
        tradeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
