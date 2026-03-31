package com.lab.narra.ams.controller.visitor;

import com.lab.narra.ams.service.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.lab.narra.ams.model.dto.UserDTO;

import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/visitor")
public class RegisterController {


    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "module/visitor/register-layout";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
            Model model) {
        // DTO validation errors **This should exist so that page return with errors
        if (result.hasErrors()) {
            return "module/visitor/register-layout";
        }
        // check email if existing in the DB
        if (userService.isUserExistByEmail(userDTO.getEmail())) {
            model.addAttribute("errorMessage", "Email Already Used.");
            model.addAttribute("userDTO", userDTO);
            return "module/visitor/register-layout";
        }
        // Save Registration Information
        userService.saveUser(userDTO);
        // Turning Get(/register) page in its initial state
        model.addAttribute("successMessage", "User Created.");
        model.addAttribute("userDTO", new UserDTO());
        return "module/visitor/register-layout";
    }

}
