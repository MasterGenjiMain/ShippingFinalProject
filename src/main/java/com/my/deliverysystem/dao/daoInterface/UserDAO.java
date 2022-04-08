package com.my.deliverysystem.dao.daoInterface;

import com.my.deliverysystem.db.entity.Role;
import com.my.deliverysystem.db.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    //create
    void add(User user) throws SQLException;

    //read
    List<User> getAll() throws SQLException;
    User getById(long id) throws SQLException;
    User getByUsername(String pattern) throws SQLException;

    //update
    boolean update(User user);

    //delete
    boolean remove(User user);
}
