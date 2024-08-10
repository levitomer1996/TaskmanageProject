package models;

public class Task {

    private String uid;
    private String user_id;
    private String title;
    private String timeToFinish;
    private Boolean isDone;
    private String priority;


    // Constructor
    public Task(String uid, String user_id, String title, String timeToFinish, Boolean isDone, String priority) {
        this.uid = uid;
        this.user_id = user_id;
        this.title = title;
        this.timeToFinish = timeToFinish;
        this.isDone = isDone;
        this.priority = priority;
    }

    // Default constructor (required for Firebase or other serialization frameworks)
    public Task() {
    }

    // Getters and Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeToFinish() {
        return timeToFinish;
    }

    public void setTimeToFinish(String timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "uid='" + uid + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", timeToFinish='" + timeToFinish + '\'' +
                ", isDone=" + isDone +
                ", priority='" + priority + '\'' +
                '}';
    }
}
