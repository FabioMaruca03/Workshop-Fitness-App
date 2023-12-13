package com.example.fitness.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@Repository
interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findAllByPersonId(UUID personId);

    List<Schedule> findAllByDayOfWeek(DayOfWeek dayOfWeek);

    List<Schedule> findAllByPersonIdAndDayOfWeek(UUID personId, DayOfWeek dayOfWeek);

}
