package com.poseidoncapitalsolutions.poseidon.controller;

import com.poseidoncapitalsolutions.poseidon.dto.UserDTO;
import com.poseidoncapitalsolutions.poseidon.service.UserService;
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
@RequestMapping("/user")
@Tag(name = "Users", description = "Manage platform user accounts (ADMIN only)")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @Operation(summary = "List users", description = "Renders the user view with all existing accounts")
    @ApiResponse(responseCode = "200", description = "User list view rendered")
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
    
    @Operation(summary = "Show add form", description = "Renders an empty form for creating a new user")
    @ApiResponse(responseCode = "200", description = "Add form rendered")
    @GetMapping("/add")
    public String addUser(UserDTO user) {
        return "user/add";
    }
    
    @Operation(summary = "Create a user", description = "Validates and persists a new user account")
    @ApiResponse(responseCode = "302", description = "User saved, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, add form redisplayed with errors")
    @PostMapping("/validate")
    public String validate(@Valid UserDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.save(user);
        return "redirect:/user/list";
    }
    
    @Operation(summary = "Show update form", description = "Loads a single user for editing by its id")
    @ApiResponse(responseCode = "200", description = "Update form rendered")
    @ApiResponse(responseCode = "404", description = "No user exists for the given id")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@Parameter(description = "Id of the user to edit") @PathVariable("id") Integer id, Model model) {
        UserDTO user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "user/update";
    }
    
    @Operation(summary = "Update a user", description = "Validates and persists changes to an existing user account")
    @ApiResponse(responseCode = "302", description = "User updated, redirected to the list view")
    @ApiResponse(responseCode = "200", description = "Validation failed, update form redisplayed with errors")
    @PostMapping("/update/{id}")
    public String updateUser(@Parameter(description = "Id of the user to update") @PathVariable("id") Integer id, @Valid UserDTO user,
                             BindingResult result, Model model) {
        user.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user/update";
        }
        userService.save(user);
        return "redirect:/user/list";
    }
    
    @Operation(summary = "Delete a user", description = "Removes a user account by its id")
    @ApiResponse(responseCode = "302", description = "User deleted, redirected to the list view")
    @ApiResponse(responseCode = "404", description = "No user exists for the given id")
    @GetMapping("/delete/{id}")
    public String deleteUser(@Parameter(description = "Id of the user to delete") @PathVariable("id") Integer id, Model model) {
        userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}
