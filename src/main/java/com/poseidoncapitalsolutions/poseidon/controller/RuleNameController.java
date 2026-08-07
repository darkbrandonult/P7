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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ruleName")
public class RuleNameController {
    
    private final RuleNameService ruleNameService;
    
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }
    
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }
    
    @GetMapping("/add")
    public String addRuleForm(RuleNameDTO ruleName) {
        return "ruleName/add";
    }
    
    @PostMapping("/validate")
    public String validate(@Valid RuleNameDTO ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameDTO ruleName = ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }
    
    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDTO ruleName,
                                 BindingResult result, Model model) {
        ruleName.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
