package dao;

import db.entity.Cargo;
import db.entity.City;
import db.entity.DeliveryOrder;

import java.sql.SQLException;
import java.util.List;

public interface CityDAO {

    //create
    void add(City city) throws SQLException;

    //read
    List<City> getAll() throws SQLException;
    City getById(Long id) throws SQLException;
    List<City> getByName(String pattern) throws SQLException;

    //update
    boolean update(City city);

    //delete
    boolean remove(City city);

}
