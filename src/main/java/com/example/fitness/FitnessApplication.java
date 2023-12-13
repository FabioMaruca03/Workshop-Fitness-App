package com.example.fitness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.modulith.Modulithic;

import java.util.Map;

@Slf4j
@SpringBootApplication
@Modulithic(sharedModules = "commons")
public class FitnessApplication {

    public static void main(String[] args) {
        if (System.getenv("spring.ai.openai.api-key") != null) {
            log.debug("Starting with AI support");
            new SpringApplicationBuilder(FitnessApplication.class)
                    .profiles("ai")
                    .run(args);
        } else {
            log.debug("Starting without AI support");
            new SpringApplicationBuilder(FitnessApplication.class)
                    .properties(Map.of("spring.autoconfigure.exclude", OpenAiAutoConfiguration.class.getName()))
                    .run(args);
        }
    }

}
