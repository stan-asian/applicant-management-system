package com.lab.narra.ams.controller.visitor;

import com.lab.narra.ams.service.user.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab.narra.ams.model.dto.TokenDTO;
import com.lab.narra.ams.model.dto.UserDTO;
import com.lab.narra.ams.model.entity.Token;
import com.lab.narra.ams.model.entity.User;
import com.lab.narra.ams.service.email.EmailService;
import com.lab.narra.ams.service.token.TokenServiceImp;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/visitor")
public class LoginController {

    private final TokenServiceImp tokenServiceImp;
    private final UserService userService;
    private final EmailService emailService;

    LoginController(EmailService emailService, UserService userService, TokenServiceImp tokenServiceImp) {
        this.emailService = emailService;
        this.userService = userService;
        this.tokenServiceImp = tokenServiceImp;
    }

    @GetMapping("/login")
    public String login(Model model) {
        if(!model.containsAttribute("userDTO")){
            model.addAttribute("userDTO", new UserDTO());
        }
        return "module/visitor/login-layout";
    }

    @PostMapping("/recover")
    public String sendEmailToChangePassword(@Valid @ModelAttribute("userDTO") UserDTO userDto, BindingResult result,
             RedirectAttributes redirectAttributes, Model model) {

        if (result.hasFieldErrors("email")) {
            model.addAttribute("userDTO", new UserDTO());
            return "module/visitor/login-layout";
        }
        if (!userService.isUserExistByEmail(userDto.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email Not Existing.");
            redirectAttributes.addFlashAttribute("userDTO", userDto);
            return "redirect:/visitor/login";
        }

        // Get user by Email
        User user = userService.getUserByEmail(userDto.getEmail());

        // Genearate Token
        String token = tokenServiceImp.generateToken();

        // Create new TokenDTO Object
        TokenDTO tokenDTO = new TokenDTO();

        tokenDTO.setToken(token);   

        // Save token to the Database
        tokenServiceImp.saveToken(tokenDTO, user);

        Map<String, Object> vars = new HashMap<>();
        vars.put("firstName", user.getFirstName());
        vars.put("lastName", user.getLastName());
        vars.put("resetLink", "http://localhost:8080/visitor/resetPassword?token=" + token);
        
        // Send Email
        emailService.sendHtmlEmail(
                user.getEmail(),
                "ApplicaTrack: Reset Password Link",
                "email/reset-pass-email-temp",
                vars);
                
        redirectAttributes.addFlashAttribute("successMessage", "Email Successfully Sent.");
        return "redirect:/visitor/login";
    }

    @GetMapping("/resetPassword")
    public String getMethodName(@RequestParam("token") String token, Model model) {

        Token userToken = tokenServiceImp.getTokenByTokenString(token);

        // check of token is expired and did not matched the token in the database
        if(userToken == null || userToken.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
            return "email/reset-pass-invalid-token";
        }

        model.addAttribute("token", token);
        return "email/reset-pass-web-page";
    }
    
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("confirmPassword") String password, RedirectAttributes redirectAttributes) {

        Token userToken = tokenServiceImp.getTokenByTokenString(token);

        // check of token is expired and did not matched the token in the database
        if(userToken == null || userToken.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
            return "email/reset-pass-invalid-token";
        }

        User user = userToken.getUser();
        // Update the password of the user and delete the token
        userService.updatePassword(user, password);

        // Delete the token after use
        tokenServiceImp.deleteToken(userToken);

        redirectAttributes.addFlashAttribute("successMessage", "Password Successfully Updated.");
        return "redirect:/visitor/login";
    }

}
