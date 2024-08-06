package com.example.taskmanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapters.TaskAdapter;
import Controller.TaskDataBaseManager;
import Interfaces.DataCallback;
import models.Task;

public class TaskListActivity extends AppCompatActivity {

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
        tdb.getAll(new DataCallback<Task>() {
            @Override
            public void onSuccess(List<Task> data) {
                tasks.clear();
                tasks.addAll(data);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle the error
            }
        });
    }

    private void createTask() {
        // Example function, might not be necessary
    }

    private void deleteTask() {
        if (!tasks.isEmpty()) {
            tasks.remove(tasks.size() - 1);
            taskAdapter.notifyItemRemoved(tasks.size());
        }
    }
}
