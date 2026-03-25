package com.lab.narra.ams.controller.visitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getMethodName() {
        return "module/visitor/login";
    }
}
