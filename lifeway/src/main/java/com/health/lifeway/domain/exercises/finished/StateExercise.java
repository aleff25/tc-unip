package com.health.lifeway.domain.exercises.finished;

public enum StateExercise {

    COMPLETED("completed"),
    CANCELLED("cancelled"),
    FINISHED("finished");

    private String name;

    StateExercise(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
