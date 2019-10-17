package com.health.lifeway.domain.exercises.available;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AvailableExerciseRepository extends JpaRepository<AvailableExercise, UUID> {

}
