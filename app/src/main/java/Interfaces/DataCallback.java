package Interfaces;

import java.util.List;

public interface DataCallback<T> {
    void onSuccess(List<T> data);
    void onFailure(Exception e);
}