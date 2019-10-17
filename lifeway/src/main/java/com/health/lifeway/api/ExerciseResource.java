package com.health.lifeway.api;

import com.health.lifeway.application.exercise.ExerciseService;
import com.health.lifeway.domain.exercises.available.AvailableExercise;
import com.health.lifeway.domain.exercises.finished.FinishedExercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/exercises")
public class ExerciseResource {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/available")
    public List<AvailableExercise> findAllAvailableExercises() {
        return this.exerciseService.findAvailableExercises();
    }

    @GetMapping("/finished")
    public List<FinishedExercise> findAllFinishedExercises() {
        return this.exerciseService.findFinishedExercises();
    }

    @PostMapping("/finished")
    public void completeExercise(@RequestBody FinishedExercise exercise) {
        this.exerciseService.completeExercise(exercise);
    }
}
