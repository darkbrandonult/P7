package com.poseidoncapitalsolutions.poseidon.controller;

import com.poseidoncapitalsolutions.poseidon.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.poseidon.service.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/curvePoint")
public class CurveController {
    
    private final CurvePointService curvePointService;
    
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }
    
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }
    
    @GetMapping("/add")
    public String addCurvePointForm(CurvePointDTO curvePoint) {
        return "curvePoint/add";
    }
    
    @PostMapping("/validate")
    public String validate(@Valid CurvePointDTO curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        curvePointService.save(curvePoint);
        return "redirect:/curvePoint/list";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePointDTO curvePoint = curvePointService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }
    
    @PostMapping("/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePoint,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointService.save(curvePoint);
        return "redirect:/curvePoint/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        curvePointService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointService.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
