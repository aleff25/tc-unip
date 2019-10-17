package com.health.lifeway.domain.exercises.finished;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FinishedExerciseRepository extends JpaRepository<FinishedExercise, UUID> {

}
