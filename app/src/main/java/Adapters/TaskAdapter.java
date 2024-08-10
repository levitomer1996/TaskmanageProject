package Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanage.R;

import java.util.List;

import models.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final String TAG = "TaskAdapter";
    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
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
    }

    @Override
    public int getItemCount() {
        return taskList.size(); // Should return the correct size of the task list
    }

    // Method to add a task
    public void addTask(Task task) {
        taskList.add(task);
        notifyItemInserted(taskList.size() - 1);
    }

    // Method to remove a task
    public void removeTask(int position) {
        if (position < taskList.size()) {
            taskList.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitleTextView;
        TextView taskDateTextView;
        RelativeLayout taskItemLayout;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitleTextView = itemView.findViewById(R.id.taskTitleTextView);
            taskDateTextView = itemView.findViewById(R.id.taskDateTextView);
            taskItemLayout = itemView.findViewById(R.id.taskItemLayout); // Make sure this matches the XML ID
        }
    }
}
