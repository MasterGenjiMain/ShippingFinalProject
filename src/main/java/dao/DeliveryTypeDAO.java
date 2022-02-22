package dao;

import db.entity.City;
import db.entity.DeliveryType;
import db.entity.Location;

import java.sql.SQLException;
import java.util.List;

public interface DeliveryTypeDAO {
    //create
    void add(DeliveryType deliveryType) throws SQLException;

    //read
    List<DeliveryType> getAll() throws SQLException;
    DeliveryType getById(Long id) throws SQLException;
    List<DeliveryType> getByName(String pattern) throws SQLException;

    //update
    boolean update(DeliveryType deliveryType);

    //delete
    boolean remove(DeliveryType deliveryType);
}
