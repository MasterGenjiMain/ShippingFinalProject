package com.my.deliverysystem.dao.daoInterface;

import com.my.deliverysystem.db.entity.Cargo;
import com.my.deliverysystem.db.entity.DeliveryOrder;

import java.sql.SQLException;
import java.util.List;

public interface CargoDAO {
    //create
    void add(Cargo cargo) throws SQLException;

    //read
    List<Cargo> getAll() throws SQLException;
    Cargo getById(Long id) throws SQLException;
    List<Cargo> getByName(String pattern) throws SQLException;

    //update
    boolean update(Cargo cargo);

    //delete
    boolean remove(Cargo cargo);
}
