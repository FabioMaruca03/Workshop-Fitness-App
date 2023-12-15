package com.example.fitness.commons.ui;

import com.example.fitness.person.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.annotation.SessionScope;

public class BaseUIController {

    @ModelAttribute("appName")
    public String getAppName(@Value("${spring.application.name}") String appName) {
        return appName;
    }

    @ModelAttribute("isProduction")
    public boolean isProduction(@Value("#{environment.matchesProfiles('prod')}") boolean isProduction) {
        return isProduction;
    }

    @ModelAttribute("isDevelopment")
    public boolean isDevelopment(@Value("#{environment.matchesProfiles('dev')}") boolean isDevelopment) {
        return isDevelopment;
    }

    @ModelAttribute("isAIEnabled")
    public boolean isArtificialIntelligenceEnabled(@Value("#{environment.matchesProfiles('ai')}") boolean enabled) {
        return enabled;
    }

    @ModelAttribute("profile")
    public Person profile(@AuthenticationPrincipal Person person) {
        return person;
    }

    @Bean
    @SessionScope
    @ConditionalOnWebApplication
    Person currentUser(@AuthenticationPrincipal Person person) {
        return person;
    }

}
