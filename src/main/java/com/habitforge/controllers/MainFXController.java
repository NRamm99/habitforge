package com.habitforge.controllers;

import java.sql.SQLException;
import java.util.List;

import com.habitforge.models.Habit;
import com.habitforge.repositories.HabitRepository;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainFXController {

    @FXML
    private ListView<Habit> habitListView;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtXpReward;

    @FXML
    private void initialize() {
        try {
            List<Habit> habits = HabitRepository.getHabits();
            habitListView.getItems().setAll(habits);
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to get habits");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void addHabit() {
        if (txtName.getText().isEmpty() || txtDescription.getText().isEmpty() || txtXpReward.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to add habit");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }
        try {
            Habit habit = new Habit(txtName.getText(), txtDescription.getText(),
                    Integer.parseInt(txtXpReward.getText()));
            HabitRepository.createHabit(habit);
            habitListView.getItems().add(habit);
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to add habit");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
