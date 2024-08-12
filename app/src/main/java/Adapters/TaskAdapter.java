package Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanage.R;

import java.util.List;

import Controller.TaskDataBaseManager;
import models.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final String TAG = "TaskAdapter";
    private List<Task> taskList;
    private TaskDataBaseManager taskDataBaseManager;

    public TaskAdapter(List<Task> taskList, TaskDataBaseManager taskDataBaseManager) {
        this.taskList = taskList;
        this.taskDataBaseManager = taskDataBaseManager;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom task item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        Log.i(TAG, "This task: " + task.toString());
        Log.i(TAG, "onBindViewHolder called for position " + position);

        holder.taskTitleTextView.setText(task.getTitle());
        holder.taskDateTextView.setText(task.getTimeToFinish());

        // Change background color based on priority
        String priority = task.getPriority();
        if (priority != null) {
            switch (priority) {
                case "LOW":
                    holder.taskItemLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_green_light));
                    break;
                case "MEDIUM":
                    holder.taskItemLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_orange_light));
                    break;
                case "HIGH":
                    holder.taskItemLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_red_light));
                    break;
                default:
                    holder.taskItemLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.white)); // Default background
                    break;
            }
        }

        // Set up the delete button
        holder.deleteTaskButton.setOnClickListener(v -> {
            deleteTask(task, position);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size(); // Should return the correct size of the task list
    }

    // Method to delete a task
    private void deleteTask(Task task, int position) {
        taskDataBaseManager.deleteById(task.getUid(), new TaskDataBaseManager.TaskDeleteCallback() {
            @Override
            public void onSuccess() {
                // Remove task from the list and notify adapter
                taskList.remove(position);
                notifyItemRemoved(position);
                Log.d(TAG, "Task deleted successfully.");
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Failed to delete task", e);
            }
        });
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitleTextView;
        TextView taskDateTextView;
        RelativeLayout taskItemLayout;
        ImageButton deleteTaskButton;  // Added reference to delete button

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitleTextView = itemView.findViewById(R.id.taskTitleTextView);
            taskDateTextView = itemView.findViewById(R.id.taskDateTextView);
            taskItemLayout = itemView.findViewById(R.id.taskItemLayout); // Ensure this matches the XML ID
            deleteTaskButton = itemView.findViewById(R.id.deleteTaskButton); // Initialize delete button
        }
    }
}
