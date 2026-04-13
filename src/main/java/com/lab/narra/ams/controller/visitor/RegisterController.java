package com.lab.narra.ams.controller.visitor;

import com.lab.narra.ams.service.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        if(!model.containsAttribute("userDTO")) {
            model.addAttribute("userDTO", new UserDTO());
        }
        return "module/visitor/register-layout";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
             RedirectAttributes redirectAttributes) {
        // DTO validation errors **This should exist so that page return with errors
        if (result.hasErrors()) {
            return "module/visitor/register-layout";
        }
        // check email if existing in the DB
        if (userService.isUserExistByEmail(userDTO.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email Already Used.");
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/visitor/register";
        }
        // Save Registration Information
        userService.saveUser(userDTO);
        // Turning Get(/register) page in its initial state
        redirectAttributes.addFlashAttribute("successMessage", "User Created.");
        redirectAttributes.addFlashAttribute("userDTO", new UserDTO());
        return "redirect:/visitor/register";
    }

}
