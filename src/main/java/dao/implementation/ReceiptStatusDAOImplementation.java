package dao.implementation;

import dao.ReceiptStatusDAO;
import db.DbManager;
import db.entity.ReceiptStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptStatusDAOImplementation implements ReceiptStatusDAO {
    private static final String INSERT_INTO_RECEIPT_STATUS = "INSERT INTO receipt_status (id, status_name) VALUES (DEFAULT, ?)";
    private static final String GET_ALL_RECEIPT_STATUSES = "SELECT id, status_name FROM receipt_status ORDER BY receipt_status.id";
    private static final String GET_RECEIPT_STATUS_BY_ID = "SELECT id, status_name FROM receipt_status WHERE receipt_status.id=? ORDER BY receipt_status.id";
    private static final String GET_RECEIPT_STATUS_BY_NAME = "SELECT id, status_name FROM receipt_status WHERE receipt_status.status_name=? ORDER BY receipt_status.id";
    private static final String UPDATE_RECEIPT_STATUS = "UPDATE receipt_status SET status_name=? WHERE id=?";
    private static final String DELETE_RECEIPT_STATUS = "DELETE FROM receipt_status WHERE id=?";

    @Override
    public void add(ReceiptStatus receiptStatus) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewReceiptStatus(receiptStatus, conn);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() receipt_status error: " + e.getMessage());
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
            System.out.println("-------------------");
            System.out.println("INSERT INTO receipt_status");
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
        Connection conn = null;
        List<ReceiptStatus> allReceiptStatuses;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allReceiptStatuses = getAllReceiptStatuses(conn);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() receipt_status error: " + e.getMessage());
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
            System.out.println("-------------------");
            System.out.println("SELECT * receipt_status");

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
        Connection conn = null;
        List<ReceiptStatus> receiptStatuses = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            receiptStatuses = getReceiptStatusById(id, conn, GET_RECEIPT_STATUS_BY_ID);
            conn.commit();

            return receiptStatuses.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() role error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<ReceiptStatus> getReceiptStatusById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReceiptStatus> receiptStatusesList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                receiptStatusesList.add(receiptStatusMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return receiptStatusesList;
    }

    @Override
    public List<ReceiptStatus> getByName(String pattern) throws SQLException {
        Connection conn = null;
        List<ReceiptStatus> receiptStatuses = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            receiptStatuses = getReceiptStatusByName(pattern, conn, GET_RECEIPT_STATUS_BY_NAME);
            conn.commit();

            return receiptStatuses;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() receipt_status error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<ReceiptStatus> getReceiptStatusByName(String pattern, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReceiptStatus> receiptStatusesList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                receiptStatusesList.add(receiptStatusMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return receiptStatusesList;
    }

    @Override
    public boolean update(ReceiptStatus receiptStatus) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_RECEIPT_STATUS);

            pstmt.setString(1, receiptStatus.getStatusName());
            pstmt.setLong(2, receiptStatus.getId());

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
    public boolean remove(ReceiptStatus receiptStatus) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_RECEIPT_STATUS);
            pstmt.setLong(1, receiptStatus.getId());

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
