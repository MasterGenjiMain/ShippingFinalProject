package com.my.deliverysystem.dao;

import com.my.deliverysystem.db.entity.Tariff;

import java.sql.SQLException;
import java.util.List;

public interface TariffDAO {
    //create
    void add(Tariff tariff) throws SQLException;

    //read
    List<Tariff> getAll() throws SQLException;
    Tariff getById(Long id) throws SQLException;
    List<Tariff> getByName(String pattern) throws SQLException;

    //update
    boolean update(Tariff tariff);

    //delete
    boolean remove(Tariff tariff);
}
