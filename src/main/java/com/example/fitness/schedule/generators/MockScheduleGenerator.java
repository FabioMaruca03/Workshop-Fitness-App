package com.example.fitness.schedule.generators;

import com.example.fitness.commons.Generator;
import com.example.fitness.schedule.Exercise;
import com.example.fitness.schedule.Schedule;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Random;

@Service
@Profile("!ai")
public class MockScheduleGenerator implements Generator<Schedule, String> {
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public Schedule generate(String bodyPart) {
        Schedule schedule = new Schedule();
        int day = random.nextInt(1, 8);

        schedule.setDayOfWeek(DayOfWeek.of(day));
        schedule.setDescription("Workout for " + bodyPart);
        schedule.setExercises(List.of(
                new Exercise(null, "Push-ups", bodyPart, "Do 20 push-ups", 2),
                new Exercise(null, "Chest press", bodyPart, "Do 20 chest presses", 2)
        ));
        return schedule;
    }
}
