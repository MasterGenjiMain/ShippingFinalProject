package dao;

import db.entity.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDAO {
    //create
    void add(Role role) throws SQLException;

    //read
    List<Role> getAll() throws SQLException;
    Role getById(Long id) throws SQLException;
    List<Role> getByName(String pattern) throws SQLException;

    //update
    boolean update(Role role);

    //delete
    boolean remove(Role role);
}
