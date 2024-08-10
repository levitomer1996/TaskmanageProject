package Controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import models.Task;

public class TaskDataBaseManager {

    private static final String TAG = "TaskDatabaseManager";
    private static final String TASKS_NODE = "task";
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    public TaskDataBaseManager() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void getAll(final TaskDataCallback callback) {
        db.collection(TASKS_NODE)
                .whereEqualTo("user_id", currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Task> tasks = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Task taskObj = document.toObject(Task.class);
                            tasks.add(taskObj);
                        }
                        callback.onSuccess(tasks);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }


    public void save(Task task) {
        String taskId = db.collection(TASKS_NODE).document().getId();
        task.setUid(taskId);
        db.collection(TASKS_NODE).document(taskId).set(task);
    }

    public interface TaskDataCallback {
        void onSuccess(List<Task> tasks);

        void onFailure(Exception e);
    }
}
