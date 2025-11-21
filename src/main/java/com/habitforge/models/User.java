package com.habitforge.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private int totalXP;
    private int level;
    private List<Habit> habits;

    public User(String username) {
        this.username = username;
        this.totalXP = 0;
        this.level = 1;
        this.habits = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public int getLevel() {
        return level;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addHabit(Habit habit) {
        this.habits.add(habit);
    }

    public void removeHabit(Habit habit) {
        this.habits.remove(habit);
    }

}
