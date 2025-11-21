package com.habitforge.controllers;

public class LevelSystem {

    public static int calculateLevel(int xp) {
        int nextLevel = 2;

        while (xp >= getRequiredXPForLevelUp(nextLevel)) {
            nextLevel++;
        }

        return (nextLevel - 1);
    }

    public static int getRequiredXPForLevelUp(int level) {
        int n = level - 1;
        return (50 * n + 5 * n * n);
    }

}
