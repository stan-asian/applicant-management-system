package com.lab.narra.ams.controller.visitor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.lab.narra.ams.model.dto.UserRegistrationDTO;
import com.lab.narra.ams.service.RegistrationService;

import jakarta.validation.Valid;

@Controller
public class RegisterController {

    private final RegistrationService registrationService;

    public RegisterController(RegistrationService regisrationService) {
        this.registrationService = regisrationService;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        // Mapping the Registration Form using DTO
        model.addAttribute("userDTO", new UserRegistrationDTO());
        model.addAttribute("pageName", "Register");
        return "module/visitor/register-layout";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userDTO") UserRegistrationDTO userDTO, BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        // DTO validation errors **This should exist so that page return with errors
        if (result.hasErrors()) {
            model.addAttribute("pageName", "Register");
            return "module/visitor/register-layout";
        }
        // check email if existing in the DB
        if (registrationService.isEmailExisting(userDTO.getEmail())) {
            model.addAttribute("errorMessage", "User Already Exist, Login Instead.");
            model.addAttribute("pageName", "Register");
            return "module/visitor/register-layout";
        }
        // Save Registration Information
        // Redirect Attributes allows you to keep the
        registrationService.saveUser(userDTO);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "User Successfully Created");
        // Technically you are just requesting here a Get request
        return "redirect:/register";
    }

}
