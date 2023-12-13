package com.example.fitness.schedule.generators;

import com.example.fitness.commons.Generator;
import com.example.fitness.schedule.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@Profile("ai")
@RequiredArgsConstructor
public class AiScheduleGenerator implements Generator<Schedule, String> {
    private final AiClient aiClient;

    @Override
    public Schedule generate(String bodyPart) {
        var parser = new BeanOutputParser<>(Schedule.class);
        String message = """
                Create a new gym schedule with 10 exercises for body part: {bodyPart}.
                The description of the exercise must be using 10 to 30 words.
                If the body part is not specified or it is invalid, the schedule will be for the whole body.
                Do not include ids in the response.
                {format}
                """;

        Prompt prompt = new PromptTemplate(message, Map.of(
                "bodyPart", bodyPart,
                "format", parser.getFormat()
        )).create();

        log.trace("Prompt: {}", prompt);

        var aiResponse = aiClient.generate(prompt);

        log.trace("AI response: {}", aiResponse.getGeneration().getText());

        Schedule generatedExercise = parser.parse(aiResponse.getGeneration().getText());
        log.debug("Generated exercise: {}", generatedExercise);

        generatedExercise.setId(null);
        generatedExercise.getExercises().forEach(exercise -> exercise.setId(null));

        return generatedExercise;
    }
}
