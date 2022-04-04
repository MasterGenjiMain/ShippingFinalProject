package com.my.deliverysystem.dao.daoInterface;

import com.my.deliverysystem.db.entity.Receipt;

import java.sql.SQLException;
import java.util.List;

public interface ReceiptDAO {
    //create
    void add(Receipt receipt) throws SQLException;

    //read
    List<Receipt> getAll() throws SQLException;
    Receipt getByReceiptId(long id) throws SQLException;
    List<Receipt> getByUserId(long id) throws SQLException;
    List<Receipt> getByManagerId(long id) throws SQLException;

    //update
    boolean update(Receipt receipt);

    //delete
    boolean remove(Receipt receipt);
}
