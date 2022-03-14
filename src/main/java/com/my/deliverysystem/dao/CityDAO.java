package com.my.deliverysystem.dao;

import com.my.deliverysystem.db.entity.City;

import java.sql.SQLException;
import java.util.List;

public interface CityDAO {

    //create
    void add(City city) throws SQLException;

    //read
    List<City> getAll() throws SQLException;
    City getById(Long id) throws SQLException;
    City getByName(String pattern) throws SQLException;

    //update
    boolean update(City city);

    //delete
    boolean remove(City city);

}
