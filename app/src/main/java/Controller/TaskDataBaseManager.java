package Controller;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import models.Task;

public class TaskDataBaseManager {

    private static final String TAG = "TaskDatabaseManager";
    private static final String TASKS_NODE = "tasks";
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    public TaskDataBaseManager() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // Handle the case where the user is not authenticated
            Log.e(TAG, "User not authenticated");
        }
    }


    public void getAll(final TaskDataCallback callback) {
        if (currentUser != null) {
            db.collection(TASKS_NODE)
                    .whereEqualTo("user_id", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Task> tasks = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Task taskObj = document.toObject(Task.class);
                                Log.i(TAG, "Fetched task:" + taskObj);
                                tasks.add(taskObj);
                            }
                            Log.d(TAG, "Fetched " + tasks.size() + " tasks");
                            callback.onSuccess(tasks);
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                            callback.onFailure(task.getException());
                        }
                    });
        } else {
            Log.e(TAG, "User not authenticated");
            callback.onFailure(new Exception("User not authenticated"));
        }
    }


    public void save(Task task) {
        String taskId = db.collection(TASKS_NODE).document().getId();
        task.setUid(taskId);
        task.setUser_id(currentUser.getUid());
        Log.i(TAG, "Saving Task with Priority: " + task.getPriority());  // Log the priority
        db.collection(TASKS_NODE).document(taskId).set(task)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Task successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing task", e));
    }

    public interface TaskDataCallback {
        void onSuccess(List<Task> tasks);

        void onFailure(Exception e);
    }
}
