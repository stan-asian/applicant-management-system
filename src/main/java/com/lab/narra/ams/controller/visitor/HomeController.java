package com.lab.narra.ams.controller.visitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visitor")
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("pageName", "Home");
        return "module/visitor/home-layout";
    }
}