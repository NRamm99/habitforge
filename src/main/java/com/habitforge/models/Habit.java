package com.habitforge.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habit {
    private String name;
    private String description;
    private int xpReward;
    private int streak;
    private List<LocalDate> completionDates;

    public Habit(String name, String description, int xpReward) {
        this.name = name;
        this.description = description;
        this.xpReward = xpReward;
        this.streak = 0;
        this.completionDates = new ArrayList<>();
    }

    public Habit(String name, String description, int xpReward, int streak) {
        this.name = name;
        this.description = description;
        this.xpReward = xpReward;
        this.streak = streak;
        this.completionDates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getXpReward() {
        return xpReward;
    }

    public int getStreak() {
        return streak;
    }

    public List<LocalDate> getCompletionDates() {
        return completionDates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public void addCompletionDate(LocalDate date) {
        this.completionDates.add(date);
    }

    @Override
    public String toString() {
        return name + " | " + description;
    }

}
