package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.dao.daoInterface.ReceiptStatusDAO;
import com.my.deliverysystem.db.entity.ReceiptStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.deliverysystem.db.DbConstants.*;

public class ReceiptStatusDAOImplementation implements ReceiptStatusDAO {

    private final Logger logger = Logger.getLogger(ReceiptStatusDAOImplementation.class);

    @Override
    public void add(ReceiptStatus receiptStatus) throws SQLException {
        logger.debug("Entered add() receiptStatusImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            insertNewReceiptStatus(receiptStatus, conn);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Add() receiptStatusImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewReceiptStatus(ReceiptStatus receiptStatus, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(INSERT_INTO_RECEIPT_STATUS, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, receiptStatus.getStatusName());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    receiptStatus.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<ReceiptStatus> getAll() throws SQLException {
        logger.debug("Entered getAll() receiptStatusImpl");
        Connection conn = null;
        List<ReceiptStatus> allReceiptStatuses;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            allReceiptStatuses = getAllReceiptStatuses(conn);
            conn.commit();

        } catch (SQLException e) {
            logger.error("getAll() receiptStatusImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allReceiptStatuses;
    }

    private List<ReceiptStatus> getAllReceiptStatuses(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<ReceiptStatus> receiptStatuses = new ArrayList<>();

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(GET_ALL_RECEIPT_STATUSES);

            while(rs.next()) {
                receiptStatuses.add(receiptStatusMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return receiptStatuses;
    }

    private ReceiptStatus receiptStatusMap(ResultSet rs) throws SQLException {
        ReceiptStatus receiptStatus = new ReceiptStatus();
        receiptStatus.setId(rs.getLong("id"));
        receiptStatus.setStatusName(rs.getString("status_name"));

        return receiptStatus;
    }

    @Override
    public ReceiptStatus getById(Long id) throws SQLException {
        logger.debug("Entered getById() receiptStatusImpl");
        Connection conn = null;
        ReceiptStatus receiptStatus;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            receiptStatus = getReceiptStatusById(id, conn);
            conn.commit();

            return receiptStatus;

        } catch (SQLException e) {
            logger.error("getById() receiptStatusImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private ReceiptStatus getReceiptStatusById(Long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReceiptStatus receiptStatus = null;
        try {
            pstmt = conn.prepareStatement(GET_RECEIPT_STATUS_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                receiptStatus = receiptStatusMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return receiptStatus;
    }

    @Override
    public ReceiptStatus getByName(String pattern) throws SQLException {
        logger.debug("Entered getByName() receiptStatusImpl");
        Connection conn = null;
        ReceiptStatus receiptStatus;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            receiptStatus = getReceiptStatusByName(pattern, conn);
            conn.commit();

            return receiptStatus;

        } catch (SQLException e) {
            logger.error("getById() receiptStatusImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private ReceiptStatus getReceiptStatusByName(String pattern, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReceiptStatus receiptStatus = null;
        try {
            pstmt = conn.prepareStatement(GET_RECEIPT_STATUS_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                receiptStatus = receiptStatusMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return receiptStatus;
    }

    @Override
    public boolean update(ReceiptStatus receiptStatus) {
        logger.debug("Entered update() receiptStatusImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_RECEIPT_STATUS);

            pstmt.setString(1, receiptStatus.getStatusName());
            pstmt.setLong(2, receiptStatus.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at update() receiptStatusImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(ReceiptStatus receiptStatus) {
        logger.debug("Entered remove() receiptStatusImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_RECEIPT_STATUS);
            pstmt.setLong(1, receiptStatus.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at remove() receiptStatusImpl" + e);
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
                logger.error("Exception at close() receiptStatusImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() receiptStatusImpl" + e);
            }
        }
    }
}
