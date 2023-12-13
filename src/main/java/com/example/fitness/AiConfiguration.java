package com.example.fitness;

import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Profile;

@Profile("ai")
@AutoConfiguration
@ImportAutoConfiguration(OpenAiAutoConfiguration.class)
public class AiConfiguration {
}
