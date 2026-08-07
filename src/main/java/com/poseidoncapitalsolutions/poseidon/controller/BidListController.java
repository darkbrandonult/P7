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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bidList")
public class BidListController {
    
    private final BidListService bidListService;
    
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }
    
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }
    
    @GetMapping("/add")
    public String addBidForm(BidListDTO bid) {
        return "bidList/add";
    }
    
    @PostMapping("/validate")
    public String validate(@Valid BidListDTO bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.save(bid);
        return "redirect:/bidList/list";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListDTO bid = bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }
    
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bid,
                            BindingResult result, Model model) {
        bid.setBidListId(id);
        if (result.hasErrors()) {
            model.addAttribute("bidList", bid);
            return "bidList/update";
        }
        bidListService.save(bid);
        return "redirect:/bidList/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        bidListService.deleteById(id);
        return "redirect:/bidList/list";
    }
}
