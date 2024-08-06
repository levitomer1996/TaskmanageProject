package Controller;

import android.util.Log;

import Interfaces.DataBaseManager;
import Interfaces.DataCallback;
import models.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TaskDataBaseManager implements DataBaseManager {

    private static final String TAG = "TaskDatabaseManager";
    private static final String TASKS_NODE = "task";
    private FirebaseFirestore db;

    public TaskDataBaseManager() {
        // Initialize Firebase Database reference
        db = FirebaseFirestore.getInstance();
    }
    @Override
    public void save(Object c) {
        Task task = (Task) c;
        String taskId = task.getUid();

    Log.i(TAG, "Saving task - " + task.toString());
        if (taskId == null) {
            taskId = db.collection(TASKS_NODE).document().getId();
            task.setUid(taskId);
        }
        if (taskId != null) {
            db.collection("task").add(task);

        } else {
            Log.e(TAG, "Error: Task ID is null.");
        }
    }

    @Override
    public  void getAll(DataCallback callback) {
        db.collection(TASKS_NODE)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Task> tasks = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Task taskObj = document.toObject(Task.class);
                            tasks.add(taskObj);
                        }
                        Log.i(TAG, "Retrieved " + tasks.size() + " tasks.");
                        callback.onSuccess(tasks);

                    } else {
                        Log.e(TAG, "Error getting tasks: ", task.getException());
                        callback.onFailure(task.getException());
                    }

                });
    }

    public FirebaseFirestore getDatabaseReference() {
        return db;
    }




}
