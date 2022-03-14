package com.my.deliverysystem.dao;

import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.ReceiptStatus;
import com.my.deliverysystem.db.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface ReceiptDAO {
    //create
    void add(Receipt receipt, User user, User manager, ReceiptStatus receiptStatus) throws SQLException;

    //read
    List<Receipt> getAll() throws SQLException;
    List<Receipt> getByUserId(Long id) throws SQLException;
    List<Receipt> getByManagerId(Long id) throws SQLException;

    //update
    boolean update(Receipt receipt);

    //delete
    boolean remove(Receipt receipt);
}
