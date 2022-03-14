package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.dao.ReceiptDAO;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.ReceiptStatus;
import com.my.deliverysystem.db.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAOImplementation implements ReceiptDAO {

    @Override
    public void add(Receipt receipt, User user, User manager, ReceiptStatus receiptStatus) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewReceipt(receipt, user, manager, receiptStatus, conn);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() receipt error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewReceipt(Receipt receipt, User user, User manager, ReceiptStatus receiptStatus, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            System.out.println("-------------------");
            System.out.println("INSERT INTO receipt");
            pstmt = conn.prepareStatement(DbConstants.INSERT_INTO_RECEIPT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, user.getId());
            pstmt.setLong(2, manager.getId());
            pstmt.setDouble(3, receipt.getPrice());
            pstmt.setLong(4, receiptStatus.getId());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Receipt> getAll() throws SQLException {
        Connection conn = null;
        List<Receipt> allReceipts;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allReceipts = getAllReceipts(conn);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() receipt error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allReceipts;
    }

    private List<Receipt> getAllReceipts(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Receipt> receipts = new ArrayList<>();

        try {
            System.out.println("-------------------");
            System.out.println("SELECT * receipt");

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

        return receipt;
    }

    @Override
    public List<Receipt> getByUserId(Long id) throws SQLException {
        return getReceipts(id, DbConstants.GET_RECEIPT_BY_USER_ID);
    }

    @Override
    public List<Receipt> getByManagerId(Long id) throws SQLException {
        return getReceipts(id, DbConstants.GET_RECEIPT_BY_MANAGER_ID);
    }

    private List<Receipt> getReceipts(Long id, String sql) throws SQLException {
        Connection conn = null;
        List<Receipt> receipts;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            receipts = getReceiptsById(id, conn, sql);
            conn.commit();

            return receipts;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getByUserId() receipt error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Receipt> getReceiptsById(Long id, Connection conn, String sql) throws SQLException {
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
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.UPDATE_RECEIPT);

            pstmt.setLong(1, receipt.getManagerId());
            pstmt.setDouble(2, receipt.getPrice());
            pstmt.setLong(3, receipt.getReceiptStatusId());
            pstmt.setLong(4, receipt.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(Receipt receipt) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.DELETE_RECEIPT);
            pstmt.setLong(1, receipt.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.out.println("rollback() error: " + e.getMessage());
            }
        }
    }
}
