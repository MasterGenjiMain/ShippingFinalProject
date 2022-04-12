package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.dao.daoInterface.ReceiptDAO;
import com.my.deliverysystem.db.entity.Receipt;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAOImplementation implements ReceiptDAO {

    private final Logger logger = Logger.getLogger(ReceiptDAOImplementation.class);

    @Override
    public void add(Receipt receipt) throws SQLException {
        logger.debug("Entered add() receiptImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            insertNewReceipt(receipt, conn);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Add() receiptImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewReceipt(Receipt receipt, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.INSERT_INTO_RECEIPT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, receipt.getUserId());
            pstmt.setLong(2, receipt.getManagerId());
            pstmt.setDouble(3, receipt.getPrice());
            pstmt.setLong(4, receipt.getReceiptStatusId());
            pstmt.setLong(5, receipt.getDeliveryOrderId());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    receipt.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Receipt> getAll() throws SQLException {
        logger.debug("Entered getAll() receiptImpl");
        Connection conn = null;
        List<Receipt> allReceipts;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            allReceipts = getAllReceipts(conn);
            conn.commit();


        } catch (SQLException e) {
            logger.error("getAll() receiptImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allReceipts;
    }

    @Override
    public Receipt getByReceiptId(long id) throws SQLException {
        logger.debug("Entered getByReceiptId() " + getClass().getName());
        Connection conn = null;
        Receipt receipt;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            receipt = getReceiptById(id, conn);
            conn.commit();

            return receipt;

        } catch (SQLException e) {
            logger.error("getById() locationImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Receipt getReceiptById(long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Receipt receipt = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_RECEIPT_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                receipt = receiptMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return receipt;
    }

    private List<Receipt> getAllReceipts(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Receipt> receipts = new ArrayList<>();

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(DbConstants.GET_ALL_RECEIPTS);

            while(rs.next()) {
                receipts.add(receiptMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return receipts;
    }

    private Receipt receiptMap(ResultSet rs) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(rs.getLong("id"));
        receipt.setUserId(rs.getLong("user_id"));
        receipt.setManagerId(rs.getLong("manager_id"));
        receipt.setPrice(rs.getDouble("price"));
        receipt.setReceiptStatusId(rs.getLong("receipt_status_id"));
        receipt.setDeliveryOrderId(rs.getLong("delivery_order_id"));

        return receipt;
    }

    @Override
    public List<Receipt> getByUserId(long id) throws SQLException {
        logger.debug("Entered getByUserId() receiptImpl");
        return getReceipts(id, DbConstants.GET_RECEIPT_BY_USER_ID);
    }

    @Override
    public List<Receipt> getByManagerId(long id) throws SQLException {
        logger.debug("Entered getByManagerId() receiptImpl");
        return getReceipts(id, DbConstants.GET_RECEIPT_BY_MANAGER_ID);
    }

    private List<Receipt> getReceipts(long id, String sql) throws SQLException {
        Connection conn = null;
        List<Receipt> receipts;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            receipts = getReceiptsById(id, conn, sql);
            conn.commit();

            return receipts;

        } catch (SQLException e) {
            logger.error("getByUserId() receiptImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Receipt> getReceiptsById(long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Receipt> receiptList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                receiptList.add(receiptMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return receiptList;
    }

    @Override
    public boolean update(Receipt receipt) {
        logger.debug("Entered update() receiptImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.UPDATE_RECEIPT);

            pstmt.setLong(1, receipt.getManagerId());
            pstmt.setDouble(2, receipt.getPrice());
            pstmt.setLong(3, receipt.getReceiptStatusId());
            pstmt.setLong(4, receipt.getDeliveryOrderId());
            pstmt.setLong(5, receipt.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at update() receiptImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(Receipt receipt) {
        logger.debug("Entered remove() receiptImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.DELETE_RECEIPT);
            pstmt.setLong(1, receipt.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at remove() receiptImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
                autoCloseable = null;
            } catch (Exception e) {
                logger.error("Exception at close() receiptImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() ReceiptImpl" + e);
            }
        }
    }
}
