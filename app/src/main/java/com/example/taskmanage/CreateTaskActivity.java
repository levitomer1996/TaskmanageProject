package com.example.taskmanage;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import Controller.TaskDataBaseManager;
import models.Task;

public class CreateTaskActivity extends AppCompatActivity {

    private TaskDataBaseManager tdb = new TaskDataBaseManager(); // Database Manager Instance
    private TextInputEditText titleEditText;
    private TextView dateTextView;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        titleEditText = findViewById(R.id.titleEditText);
        dateTextView = findViewById(R.id.dateTextView);
        Button datePickerButton = findViewById(R.id.datePickerButton);
        Button saveButton = findViewById(R.id.saveButton);

        calendar = Calendar.getInstance();

        datePickerButton.setOnClickListener(v -> showDatePickerDialog());

        saveButton.setOnClickListener(v -> saveTask());
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateTextView();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateTextView() {
        String selectedDate = android.text.format.DateFormat.format("MM/dd/yyyy", calendar).toString();
        dateTextView.setText(selectedDate);
    }

    private void saveTask() {
        String title = titleEditText.getText().toString();
        String date = dateTextView.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Task object
        Task task = new Task(null, null,title, date, false);

        // Save the task using TaskDataBaseManager
        tdb.save(task);

        Toast.makeText(this, "Task saved successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after saving
    }
}
