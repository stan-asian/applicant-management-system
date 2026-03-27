package com.lab.narra.ams.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/user")
public class DashboardController {

    @GetMapping("/dashboard")
    public String getMethodName(Model model) {
        return "module/user/dashboard-layout";
    }
    
}
