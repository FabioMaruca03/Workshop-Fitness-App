package com.example.fitness.commons.ui;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("dev")
@RequestMapping("/tester")
public class TesterController extends BaseUIController {

    @GetMapping
    public String tester() {
        return "redirect:/tester/global/variables";
    }

    @GetMapping("/global/variables")
    public String globalVariables() {
        return "dev/test-global-variables";
    }

}
