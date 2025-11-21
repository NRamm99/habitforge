package com.habitforge.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.habitforge.models.Habit;
import com.habitforge.repositories.HabitCompletionDatesRepository;
import com.habitforge.repositories.HabitRepository;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainFXController {

    private Habit focusedHabit;

    @FXML
    private DatePicker completedDatePicker;

    @FXML
    private ListView<Habit> habitListView;

    @FXML
    private ListView<Habit> datePickerHabitsListView;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtXpReward;

    @FXML
    private ListView<LocalDate> completedDateListView;

    @FXML
    private void initialize() {
        datePickerHabitsListView.setOnMouseClicked(e -> {
            System.out.println("Mouse clicked ON THE LISTVIEW ITSELF");
        });
        loadHabits();
        loadDatePickerHabitsListView();
        habitListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                focusedHabit = newVal;
                // update the second list
                datePickerHabitsListView.getSelectionModel().select(newVal);
                loadCompletedDates();
            }
        });
        datePickerHabitsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                focusedHabit = newVal;
                // update the first list
                habitListView.getSelectionModel().select(newVal);
                loadCompletedDates();
            }
        });
        
    }

    private void loadDatePickerHabitsListView() {
        try {
            List<Habit> habits = HabitRepository.getHabits();
            datePickerHabitsListView.getItems().setAll(habits);
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to get habits");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadCompletedDates() {
        if (focusedHabit == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No habit focused");
            alert.setContentText("Please select a habit");
            alert.showAndWait();
            return;
        }
        try {
            var dates = HabitCompletionDatesRepository.getCompletionDatesByName(focusedHabit.getName());
            System.out.println("Focused habit: " + focusedHabit.getName());
            System.out.println("Loaded dates: " + dates.size() + " -> " + dates);

            completedDateListView.getItems().setAll(dates);
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to get completion dates");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadHabits() {
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
        loadHabits();
        loadCompletedDates();
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
            txtName.clear();
            txtDescription.clear();
            txtXpReward.clear();
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to add habit");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void addDate() {
        focusedHabit = datePickerHabitsListView.getSelectionModel().getSelectedItem();
        if (focusedHabit != null) {
            LocalDate date = completedDatePicker.getValue();
            if (date != null) {
                try {
                    HabitCompletionDatesRepository.addCompletionDate(focusedHabit, date);
                } catch (SQLException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to add completion date");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No habit focused");
            alert.setContentText("Please select a habit");
            alert.showAndWait();
        }
        loadDatePickerHabitsListView();
    }

    @FXML
    private void updateHabitLists() {
        loadHabits();
    }

    @FXML
    private void updateDatesLists() {
        loadDatePickerHabitsListView();
    }
}
