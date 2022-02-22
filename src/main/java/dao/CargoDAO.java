package dao;

import db.entity.Cargo;
import db.entity.DeliveryOrder;

import java.sql.SQLException;
import java.util.List;

public interface CargoDAO {
    //create
    void add(Cargo cargo, DeliveryOrder deliveryOrder) throws SQLException;

    //read
    List<Cargo> getAll() throws SQLException;
    Cargo getById(Long id) throws SQLException;
    List<Cargo> getByName(String pattern) throws SQLException;

    //update
    boolean update(Cargo cargo);

    //delete
    boolean remove(Cargo cargo);
}
