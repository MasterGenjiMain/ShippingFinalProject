package com.my.deliverysystem.dao.daoInterface;

import com.my.deliverysystem.db.entity.DeliveryType;

import java.sql.SQLException;
import java.util.List;

public interface DeliveryTypeDAO {
    //create
    void add(DeliveryType deliveryType) throws SQLException;

    //read
    List<DeliveryType> getAll() throws SQLException;
    DeliveryType getById(long id) throws SQLException;
    DeliveryType getByName(String pattern) throws SQLException;
    List<DeliveryType> getByLanguageId(long id) throws SQLException;

    //update
    boolean update(DeliveryType deliveryType);

    //delete
    boolean remove(DeliveryType deliveryType);
}
