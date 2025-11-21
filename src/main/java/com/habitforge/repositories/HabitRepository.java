package com.habitforge.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.habitforge.config.DatabaseConfig;
import com.habitforge.models.Habit;

public class HabitRepository {

    private HabitRepository() {
    }

    public static void createHabit(Habit habit) throws SQLException {
        String sql = "INSERT INTO habits (name, description, xpReward, streak) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, habit.getName());
            stmt.setString(2, habit.getDescription());
            stmt.setInt(3, habit.getXpReward());
            stmt.setInt(4, habit.getStreak());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to create habit: " + e);
        }
    }

    public static void removeHabit(Habit habit) throws SQLException {
        String sql = "DELETE FROM habits WHERE name = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, habit.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to remove habit: " + e);
        }
    }

    public static void updateHabit(Habit habit) throws SQLException {
        String sql = "UPDATE habits SET description = ?, xpReward = ?, streak = ? WHERE name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, habit.getDescription());
            stmt.setInt(2, habit.getXpReward());
            stmt.setInt(3, habit.getStreak());
            stmt.setString(4, habit.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update habit: " + e);
        }
    }

    public static List<Habit> getHabits() throws SQLException {
        String sql = "SELECT * FROM habits";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Habit> habits = new ArrayList<>();
            while (rs.next()) {
                habits.add(new Habit(rs.getString("name"), rs.getString("description"), rs.getInt("xpReward"),
                        rs.getInt("streak")));
            }
            return habits;
        } catch (SQLException e) {
            throw new SQLException("Failed to get habits: " + e);
        }
    }

}
