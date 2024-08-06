package Interfaces;

import java.util.List;

import models.Task;

public interface DataBaseManager<T> {
    public void save(T c);
    public void getAll(DataCallback<T> callback);
}
