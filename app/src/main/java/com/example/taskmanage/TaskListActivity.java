package com.example.taskmanage;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapters.TaskAdapter;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<String> tasks;

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
                createTask();
            }
        });

        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });
    }

    private void createTask() {
        tasks.add("Task " + (tasks.size() + 1));
        taskAdapter.notifyItemInserted(tasks.size() - 1);
    }

    private void deleteTask() {
        if (!tasks.isEmpty()) {
            tasks.remove(tasks.size() - 1);
            taskAdapter.notifyItemRemoved(tasks.size());
        }
    }
}
