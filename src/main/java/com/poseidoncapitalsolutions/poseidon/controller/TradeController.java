package com.poseidoncapitalsolutions.poseidon.controller;

import com.poseidoncapitalsolutions.poseidon.dto.TradeDTO;
import com.poseidoncapitalsolutions.poseidon.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trade")
public class TradeController {
    
    private final TradeService tradeService;
    
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }
    
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }
    
    @GetMapping("/add")
    public String addTradeForm(TradeDTO trade) {
        return "trade/add";
    }
    
    @PostMapping("/validate")
    public String validate(@Valid TradeDTO trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.save(trade);
        return "redirect:/trade/list";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        TradeDTO trade = tradeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }
    
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO trade,
                              BindingResult result, Model model) {
        trade.setTradeId(id);
        if (result.hasErrors()) {
            model.addAttribute("trade", trade);
            return "trade/update";
        }
        tradeService.save(trade);
        return "redirect:/trade/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
