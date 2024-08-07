package Interfaces;

import java.util.List;

public interface DataBaseManager<T> {
    public void save(T c);

    public List<T> getAll();
}
