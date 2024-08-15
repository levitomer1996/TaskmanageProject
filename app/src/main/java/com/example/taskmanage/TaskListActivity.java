package com.example.taskmanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasks, tdb);
        taskRecyclerView.setAdapter(taskAdapter);

        setSupportActionBar(findViewById(R.id.toolbar)); // Set the toolbar as the app bar

        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to CreateTaskActivity
                Intent intent = new Intent(TaskListActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });

        loadTasks();
    }

    private void loadTasks() {
        Log.d(TAG, "Loading tasks...");
        progressBar.setVisibility(View.VISIBLE); // Show the progress spinner

        tdb.getAll(new TaskDataBaseManager.TaskDataCallback() {
            @Override
            public void onSuccess(List<Task> fetchedTasks) {
                tasks.clear();  // Clear the old list
                tasks.addAll(fetchedTasks);  // Add all fetched tasks
                taskAdapter.notifyDataSetChanged();  // Notify the adapter to refresh the RecyclerView
                progressBar.setVisibility(View.GONE); // Hide the progress spinner
                Log.d(TAG, "Loaded " + tasks.size() + " tasks.");
            }

            @Override
            public void onFailure(Exception e) {
                progressBar.setVisibility(View.GONE); // Hide the progress spinner
                Log.e(TAG, "Error loading tasks", e);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_task_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signout) { // Updated this line
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();  // Sign out from Firebase
        Intent intent = new Intent(TaskListActivity.this, SigninActivity.class);  // Redirect to sign-in activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();  // Finish this activity
    }
}
