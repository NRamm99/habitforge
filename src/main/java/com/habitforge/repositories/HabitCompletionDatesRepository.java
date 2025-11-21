package com.habitforge.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.habitforge.config.DatabaseConfig;
import com.habitforge.models.Habit;

public class HabitCompletionDatesRepository {
    private HabitCompletionDatesRepository() {
    }

    public static void addCompletionDate(Habit habit, LocalDate date) throws SQLException {
        String sql = "INSERT INTO habit_completion_dates (habit_name, completion_date) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, habit.getName());
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to add completion date: " + e);
        }
    }

    public static List<LocalDate> getCompletionDatesByName(String name) throws SQLException {
        String sql = "SELECT completion_date FROM habit_completion_dates WHERE habit_name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            List<LocalDate> dates = new ArrayList<>();
            while (rs.next()) {
                dates.add(rs.getDate("completion_date").toLocalDate());
            }
            return dates;
        } catch (SQLException e) {
            throw new SQLException("Failed to get completion dates by name: " + e);
        }
    }
}
