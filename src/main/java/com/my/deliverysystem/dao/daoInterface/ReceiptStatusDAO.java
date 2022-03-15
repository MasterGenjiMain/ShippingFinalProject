package com.my.deliverysystem.dao.daoInterface;

import com.my.deliverysystem.db.entity.ReceiptStatus;

import java.sql.SQLException;
import java.util.List;

public interface ReceiptStatusDAO {
    //create
    void add(ReceiptStatus receiptStatus) throws SQLException;

    //read
    List<ReceiptStatus> getAll() throws SQLException;
    ReceiptStatus getById(Long id) throws SQLException;
    ReceiptStatus getByName(String pattern) throws SQLException;

    //update
    boolean update(ReceiptStatus receiptStatus);

    //delete
    boolean remove(ReceiptStatus receiptStatus);
}
