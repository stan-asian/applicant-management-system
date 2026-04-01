package com.lab.narra.ams.controller.visitor;

import com.lab.narra.ams.service.user.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab.narra.ams.model.dto.UserDTO;
import com.lab.narra.ams.service.email.EmailService;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/visitor")
public class LoginController {

    private final UserService userService;
    private final EmailService emailService;

    LoginController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "module/visitor/login-layout";
    }

    @PostMapping("/recover")
    public String sendEmailToChangePassword(@Valid @ModelAttribute("userDTO") UserDTO userDto, BindingResult result,
            Model model) {

        if (result.hasFieldErrors("email")) {
            model.addAttribute("userDTO", new UserDTO());
            return "module/visitor/login-layout";
        }

        if (!userService.isUserExistByEmail(userDto.getEmail())) {
            model.addAttribute("errorMessage", "Email Not Existing.");
            model.addAttribute("userDTO", userDto);
            return "module/visitor/login-layout";
        }

        UserDTO user = userService.getUserByEmail(userDto.getEmail());

        // Genearate Token
        

        Map<String, Object> vars = new HashMap<>();
        vars.put("firstName", user.getFirstName());
        vars.put("lastName", user.getLastName());
        vars.put("resetLink", "https://applicatrack.com/reset-password?token=abc123");

        // Send Email
        emailService.sendHtmlEmail(
                user.getEmail(),
                "ApplicaTrack: Reset Password Link",
                "email/reset-pass-email-temp",
                vars);
                
        model.addAttribute("successMessage", "Email Successfully Sent.");
        model.addAttribute("userDTO", new UserDTO());
        return "module/visitor/login-layout";
    }

}
