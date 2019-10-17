package com.health.lifeway.application.exercise.impl;

import com.health.lifeway.application.exercise.ExerciseService;
import com.health.lifeway.domain.exercises.available.AvailableExercise;
import com.health.lifeway.domain.exercises.available.AvailableExerciseRepository;
import com.health.lifeway.domain.exercises.finished.FinishedExercise;
import com.health.lifeway.domain.exercises.finished.FinishedExerciseRepository;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {

    private static final Logger LOG = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    private AvailableExerciseRepository availableExerciseRepository;
    private FinishedExerciseRepository finishedExerciseRepository;

    @Autowired
    public ExerciseServiceImpl(AvailableExerciseRepository availableExerciseRepository,
                               FinishedExerciseRepository finishedExerciseRepository) {
        this.availableExerciseRepository = availableExerciseRepository;
        this.finishedExerciseRepository = finishedExerciseRepository;
    }

    @Override
    public List<AvailableExercise> findAvailableExercises() {
        LOG.info("Fetching all available exercises");
        return this.availableExerciseRepository.findAll();
    }

    @Override
    public List<FinishedExercise> findFinishedExercises() {
        LOG.info("Fetching all finished exercises");
        return this.finishedExerciseRepository.findAll();
    }

    @Override
    public void completeExercise(FinishedExercise exercise) {
        Validate.notNull(exercise, "Please, inform an exercise");
        LOG.info("Saving exercise {}", exercise);
        this.finishedExerciseRepository.save(exercise);
    }
}
