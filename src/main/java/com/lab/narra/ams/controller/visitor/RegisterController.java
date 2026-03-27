package com.lab.narra.ams.controller.visitor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.lab.narra.ams.model.dto.UserRegistrationDTO;
import com.lab.narra.ams.service.RegistrationService;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;



@Controller
public class RegisterController {

    private final RegistrationService registrationService;

    public RegisterController(RegistrationService regisrationService) {
        this.registrationService = regisrationService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("pageName", "Register");
        model.addAttribute("userDTO", new UserRegistrationDTO());
        return "module/visitor/register-layout";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("userDTO") UserRegistrationDTO userDTO, BindingResult result,
            Model model) {
        // DTO validation errors **This should exist so that page return with errors
        if (result.hasErrors()) {
            model.addAttribute("pageName", "Register");
            return "module/visitor/register-layout";
        }
        // check email if existing in the DB
        if (registrationService.isEmailExisting(userDTO.getEmail())) {
            model.addAttribute("errorMessage", "User Already Exist, Login Instead.");
            model.addAttribute("pageName", "Register");
            model.addAttribute("userDTO", userDTO);
            return "module/visitor/register-layout";
        }
        // Save Registration Information
        registrationService.saveUser(userDTO);
        // Turning Get(/register) page in its initial state
        model.addAttribute("successMessage", "User Successfully created.");
        model.addAttribute("pageName", "Register");
        model.addAttribute("userDTO", new UserRegistrationDTO());
        return "module/visitor/register-layout";
    }

}
