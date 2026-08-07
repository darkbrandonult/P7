package com.poseidoncapitalsolutions.poseidon.controller;

import com.poseidoncapitalsolutions.poseidon.dto.UserDTO;
import com.poseidoncapitalsolutions.poseidon.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
    
    @GetMapping("/add")
    public String addUser(UserDTO user) {
        return "user/add";
    }
    
    @PostMapping("/validate")
    public String validate(@Valid UserDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.save(user);
        return "redirect:/user/list";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        UserDTO user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user/update";
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDTO user,
                             BindingResult result, Model model) {
        user.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user/update";
        }
        userService.save(user);
        return "redirect:/user/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}
