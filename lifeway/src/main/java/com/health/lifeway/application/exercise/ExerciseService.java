package com.health.lifeway.application.exercise;

import com.health.lifeway.domain.exercises.available.AvailableExercise;
import com.health.lifeway.domain.exercises.finished.FinishedExercise;

import java.util.List;

public interface ExerciseService {

    List<AvailableExercise> findAvailableExercises();

    List<FinishedExercise> findFinishedExercises();

    void completeExercise(FinishedExercise exercise);
}
