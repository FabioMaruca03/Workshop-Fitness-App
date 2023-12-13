package com.example.fitness.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleRestController {
    private final ScheduleService scheduleService;

    @PostMapping("/generate")
    public ResponseEntity<Schedule> createSchedule(@RequestParam String bodyPart) {
        return ResponseEntity.ok(scheduleService.generateSchedule(UUID.randomUUID(), bodyPart));
    }

    @PostMapping("/")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.save(schedule));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable UUID id) {
        return ResponseEntity.of(scheduleService.findById(id));
    }

    @GetMapping("/of/{personId}")
    public List<Schedule> findAll(@PathVariable UUID personId) {
        return scheduleService.findAll(personId);
    }

}
