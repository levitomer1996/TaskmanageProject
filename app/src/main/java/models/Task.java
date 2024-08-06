package models;

public class Task {

    private String uid;
    private String title;
    private String timeToFinish;
    private Boolean isDone;

    // Constructor
    public Task(String uid, String title, String timeToFinish, Boolean isDone) {
        this.uid = uid;
        this.title = title;
        this.timeToFinish = timeToFinish;
        this.isDone = isDone;
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

    @Override
    public String toString() {
        return "Task{" +
                "uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", timeToFinish='" + timeToFinish + '\'' +
                ", isDone=" + isDone +
                '}';
    }
}
