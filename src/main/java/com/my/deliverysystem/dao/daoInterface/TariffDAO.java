package com.my.deliverysystem.dao.daoInterface;

import com.my.deliverysystem.db.entity.Tariff;

import java.sql.SQLException;
import java.util.List;

public interface TariffDAO {
    //create
    void add(Tariff tariff) throws SQLException;

    //read
    List<Tariff> getAll() throws SQLException;
    Tariff getById(long id) throws SQLException;
    Tariff getByName(String pattern) throws SQLException;
    List<Tariff> getByLanguageId(long id) throws SQLException;
    //update
    boolean update(Tariff tariff);

    //delete
    boolean remove(Tariff tariff);
}
