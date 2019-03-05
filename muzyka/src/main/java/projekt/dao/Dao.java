package projekt.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(int id);
    List<T> getAll();
    void save(T o);
    void update(T o) throws IllegalArgumentException;
    void delete(T o);
}