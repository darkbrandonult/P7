package com.poseidoncapitalsolutions.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidoncapitalsolutions.poseidon.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.poseidon.service.RuleNameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ruleName")
@Tag(name = "Rule Names", description = "Manage trading rule name definitions")
public class RuleNameController {
    
    private final RuleNameService ruleNameService;
    
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }
    
    @Operation(summary = "List rule names", description = "Renders the rule name view with all existing entries")
    @ApiResponse(responseCode = "200", description = "Rule name list view rendered")
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }
    
    @Operation(summary = "Show add form", description = "Renders an empty form for creating a new rule name")
    @ApiResponse(responseCode = "200", description = "Add form rendered")
    @GetMapping("/add")
    public String addRuleForm(RuleNameDTO ruleName) {
        return "ruleName/add";
    }
    
    @Operation(summary = "Create a rule name", description = "Validates and persists a new rule name")
    @ApiResponse(responseCode = "302", description = "Rule name saved, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, add form redisplayed with errors")
    @PostMapping("/validate")
    public String validate(@Valid RuleNameDTO ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }
    
    @Operation(summary = "Show update form", description = "Loads a single rule name for editing by its id")
    @ApiResponse(responseCode = "200", description = "Update form rendered")
    @ApiResponse(responseCode = "404", description = "No rule name exists for the given id")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@Parameter(description = "Id of the rule name to edit") @PathVariable("id") Integer id, Model model) {
        RuleNameDTO ruleName = ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }
    
    @Operation(summary = "Update a rule name", description = "Validates and persists changes to an existing rule name")
    @ApiResponse(responseCode = "302", description = "Rule name updated, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, update form redisplayed with errors")
    @PostMapping("/update/{id}")
    public String updateRuleName(@Parameter(description = "Id of the rule name to update") @PathVariable("id") Integer id, @Valid RuleNameDTO ruleName,
                                 BindingResult result, Model model) {
        ruleName.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }
    
    @Operation(summary = "Delete a rule name", description = "Removes a rule name by its id")
    @ApiResponse(responseCode = "302", description = "Rule name deleted, redirected to the list view")
    @ApiResponse(responseCode = "404", description = "No rule name exists for the given id")
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@Parameter(description = "Id of the rule name to delete") @PathVariable("id") Integer id, Model model) {
        ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
