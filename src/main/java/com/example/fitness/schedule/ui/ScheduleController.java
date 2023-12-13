package com.example.fitness.schedule.ui;

import com.example.fitness.commons.ui.BaseUIController;
import com.example.fitness.schedule.Schedule;
import com.example.fitness.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedules")
@PreAuthorize("isAuthenticated()")
public class ScheduleController extends BaseUIController {

    private final ScheduleService scheduleService;

    @GetMapping
    public String schedules(Model model, RedirectAttributes redirectAttributes) {
        if (!redirectAttributes.containsAttribute("schedule")) {
            Schedule chest = scheduleService.generateSchedule(UUID.randomUUID(), "chest");
            model.addAttribute("schedule", chest);
        }

        model.addAllAttributes(redirectAttributes.getFlashAttributes());

        return "schedule/schedule";
    }

    @GetMapping("/day/{day}")
    public String schedulesForDay(@PathVariable DayOfWeek day, RedirectAttributes redirect) {
        redirect.addFlashAttribute("schedules", scheduleService.findAll(day));

        return "redirect:/schedules";
    }

    @GetMapping("/{scheduleId}")
    public String scheduleDetails(@PathVariable UUID scheduleId, RedirectAttributes redirect) {
        redirect.addFlashAttribute("schedule", scheduleService.findById(scheduleId));

        return "redirect:/schedules";
    }

    @PostMapping("/create")
    public String createSchedule(@RequestParam String bodyPart, RedirectAttributes redirect) {
        final var schedule = scheduleService.generateSchedule(UUID.randomUUID(), bodyPart);
        redirect.addFlashAttribute("schedule", schedule);

        return "redirect:/schedules";
    }

}
