package com.example.fitness.schedule;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Data
@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID personId;

    private String description;

    @OneToMany(cascade = {PERSIST, DETACH, MERGE}, fetch = EAGER)
    private List<Exercise> exercises;

    private DayOfWeek dayOfWeek;

}
