package com.health.lifeway.domain.exercises.finished;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class FinishedExercise {

    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private int duration;

    @Column
    private int calories;

    @Enumerated(EnumType.STRING)
    @Column
    private StateExercise state;

    @Column
    private LocalDateTime date;

    public FinishedExercise() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public StateExercise getState() {
        return state;
    }

    public void setState(StateExercise state) {
        this.state = state;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
