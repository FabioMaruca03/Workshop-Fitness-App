package com.example.fitness.schedule;

import com.example.fitness.commons.Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final Generator<Schedule, String> scheduleGenerator;

    public Schedule generateSchedule(UUID personId, String bodyPart) {
        Schedule schedule = scheduleGenerator.generate(bodyPart);
        schedule.setPersonId(personId);

        log.trace("Generated schedule: {}", schedule);
        return scheduleRepository.save(schedule);
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Optional<Schedule> findById(UUID id) {
        return scheduleRepository.findById(id);
    }

    public List<Schedule> findByPersonId(UUID personId) {
        return scheduleRepository.findAllByPersonId(personId);
    }

    public List<Schedule> findAll(UUID personId, DayOfWeek dayOfWeek) {
        return scheduleRepository.findAllByPersonIdAndDayOfWeek(personId, dayOfWeek);
    }

    public List<Schedule> findAll(DayOfWeek dayOfWeek) {
        return scheduleRepository.findAllByDayOfWeek(dayOfWeek);
    }

    public List<Schedule> findAll(UUID personId) {
        return scheduleRepository.findAllByPersonId(personId);
    }

    public void deleteById(UUID id) {
        scheduleRepository.deleteById(id);
    }

}
