package com.poseidoncapitalsolutions.poseidon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidoncapitalsolutions.poseidon.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.poseidon.service.CurvePointService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/curvePoint")
@Tag(name = "Curve Points", description = "Manage yield curve point data")
public class CurveController {
    
    private final CurvePointService curvePointService;
    
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }
    
    @Operation(summary = "List curve points", description = "Renders the curve point view with all existing entries")
    @ApiResponse(responseCode = "200", description = "Curve point list view rendered")
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }
    
    @Operation(summary = "Show add form", description = "Renders an empty form for creating a new curve point")
    @ApiResponse(responseCode = "200", description = "Add form rendered")
    @GetMapping("/add")
    public String addCurvePointForm(CurvePointDTO curvePoint) {
        return "curvePoint/add";
    }
    
    @Operation(summary = "Create a curve point", description = "Validates and persists a new curve point")
    @ApiResponse(responseCode = "302", description = "Curve point saved, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, add form redisplayed with errors")
    @PostMapping("/validate")
    public String validate(@Valid CurvePointDTO curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        curvePointService.save(curvePoint);
        return "redirect:/curvePoint/list";
    }
    
    @Operation(summary = "Show update form", description = "Loads a single curve point for editing by its id")
    @ApiResponse(responseCode = "200", description = "Update form rendered")
    @ApiResponse(responseCode = "404", description = "No curve point exists for the given id")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@Parameter(description = "Id of the curve point to edit") @PathVariable("id") Integer id, Model model) {
        CurvePointDTO curvePoint = curvePointService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }
    
    @Operation(summary = "Update a curve point", description = "Validates and persists changes to an existing curve point")
    @ApiResponse(responseCode = "302", description = "Curve point updated, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, update form redisplayed with errors")
    @PostMapping("/update/{id}")
    public String updateCurvePoint(@Parameter(description = "Id of the curve point to update") @PathVariable("id") Integer id, @Valid CurvePointDTO curvePoint,
                                   BindingResult result, Model model) {
        curvePoint.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        }
        curvePointService.save(curvePoint);
        return "redirect:/curvePoint/list";
    }
    
    @Operation(summary = "Delete a curve point", description = "Removes a curve point by its id")
    @ApiResponse(responseCode = "302", description = "Curve point deleted, redirected to the list view")
    @ApiResponse(responseCode = "404", description = "No curve point exists for the given id")
    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@Parameter(description = "Id of the curve point to delete") @PathVariable("id") Integer id, Model model) {
        curvePointService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointService.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
