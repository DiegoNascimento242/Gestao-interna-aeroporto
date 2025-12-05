package dao;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<T> {
    

    int create(T entity) throws SQLException;
    T readById(int id) throws SQLException;

    List<T> readAll() throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean delete(int id) throws SQLException;
}
