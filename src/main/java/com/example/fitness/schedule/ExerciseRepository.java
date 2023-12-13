package com.example.fitness.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    List<Exercise> findAllByBodyPart(String bodyPart);

    List<Exercise> findAllByNameLike(String name);

}
