package com.example.taskmanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapters.TaskAdapter;
import Controller.TaskDataBaseManager;
import models.Task;

public class TaskListActivity extends AppCompatActivity {

    private final String TAG = "TaskListActivity";
    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;
    private TaskDataBaseManager tdb = new TaskDataBaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasks);
        taskRecyclerView.setAdapter(taskAdapter);

        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to CreateTaskActivity
                Intent intent = new Intent(TaskListActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });

        loadTasks();
    }

    private void loadTasks() {
        Log.d(TAG, "Loading tasks...");

        tdb.getAll(new TaskDataBaseManager.TaskDataCallback() {
            @Override
            public void onSuccess(List<Task> fetchedTasks) {
                tasks.clear();  // Clear the old list
                tasks.addAll(fetchedTasks);  // Add all fetched tasks
                taskAdapter.notifyDataSetChanged();  // Notify the adapter to refresh the RecyclerView
                Log.d(TAG, "Loaded " + tasks.size() + " tasks.");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Error loading tasks", e);
            }
        });
    }

    private void createTask() {
        // Example task creation
        Task newTask = new Task(null, null, "New Task", "12/31/2024", false);
        taskAdapter.addTask(newTask);
        // You might want to save this task to the database using tdb.save(newTask);
        tdb.save(newTask);
    }

    private void deleteTask() {
        if (!tasks.isEmpty()) {
            int lastIndex = tasks.size() - 1;
            taskAdapter.removeTask(lastIndex);
            // Optionally remove from the database as well
        }
    }
}