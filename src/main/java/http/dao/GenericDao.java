package http.dao;

import java.sql.SQLDataException;
import java.util.List;

public interface GenericDao <T>{
    List<T> readAll();
    void create(T entity) throws SQLDataException;
    T read(int id);
    void update(T entity) throws SQLDataException;
    void delete(int id);
}
