package dao;

import db.entity.Role;
import db.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    //create
    void add(User user, Role role) throws SQLException;

    //read
    List<User> getAll() throws SQLException;
    User getById(Long id) throws SQLException;
    List<User> getByUsername(String pattern) throws SQLException;

    //update
    boolean update(User user);

    //delete
    boolean remove(User user);
}
