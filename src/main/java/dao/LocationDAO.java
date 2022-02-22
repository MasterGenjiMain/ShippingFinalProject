package dao;

import db.entity.City;
import db.entity.Location;

import java.sql.SQLException;
import java.util.List;

public interface LocationDAO {
    //create
    void add(Location location, City city) throws SQLException;

    //read
    List<Location> getAll() throws SQLException;
    Location getById(Long id) throws SQLException;
    List<Location> getByName(String pattern) throws SQLException;

    //update
    boolean update(Location location);

    //delete
    boolean remove(Location location);
}
