package com.my.deliverysystem.dao.daoInterface;

import com.my.deliverysystem.db.entity.DeliveryOrder;

import java.sql.SQLException;
import java.util.List;

public interface DeliveryOrderDAO {
    //create
    void add(DeliveryOrder deliveryOrder) throws SQLException;

    //read
    List<DeliveryOrder> getAll() throws SQLException;
    DeliveryOrder getById(long id) throws SQLException;
    List<DeliveryOrder> getByLocationFromId(Long id) throws SQLException;
    List<DeliveryOrder> getByLocationToId(Long id) throws SQLException;

    //update
    boolean update(DeliveryOrder deliveryOrder);

    //delete
    boolean remove(DeliveryOrder deliveryOrder);
}
