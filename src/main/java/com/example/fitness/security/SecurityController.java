package com.example.fitness.security;

import com.example.fitness.commons.ui.BaseUIController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController extends BaseUIController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        model.addAttribute("loginError", error != null);
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

}
