package dao.implementation;

import dao.TariffDAO;
import db.DbManager;
import db.entity.Tariff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TariffDAOImplementation implements TariffDAO {
    private static final String INSERT_INTO_TARIFF = "INSERT INTO tariff (id, tariff_name, tariff_price, tariff_info) VALUES (DEFAULT, ?, ?, ?)";
    private static final String GET_ALL_TARIFFS = "SELECT id, tariff_name, tariff_price, tariff_info FROM tariff ORDER BY tariff.id";
    private static final String GET_TARIFF_BY_ID = "SELECT id, tariff_name, tariff_price, tariff_info FROM tariff WHERE tariff.id=? ORDER BY tariff.id";
    private static final String GET_TARIFF_BY_NAME = "SELECT id, tariff_name, tariff_price, tariff_info FROM tariff WHERE tariff.tariff_name=? ORDER BY tariff.id";
    private static final String UPDATE_TARIFF = "UPDATE tariff SET tariff_name=?, tariff_price=?, tariff_info=? WHERE id=?";
    private static final String DELETE_TARIFF = "DELETE FROM tariff WHERE id=?";

    @Override
    public void add(Tariff tariff) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewTariff(tariff, conn);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() tariff error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewTariff(Tariff tariff, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            System.out.println("-------------------");
            System.out.println("INSERT INTO tariff");
            pstmt = conn.prepareStatement(INSERT_INTO_TARIFF, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, tariff.getTariffName());
            pstmt.setDouble(2, tariff.getTariffPrice());
            pstmt.setString(3, tariff.getTariffInfo());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    tariff.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Tariff> getAll() throws SQLException {
        Connection conn = null;
        List<Tariff> allTariffs;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allTariffs = getAllTariffs(conn);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() tariff error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allTariffs;
    }

    private List<Tariff> getAllTariffs(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Tariff> tariffs = new ArrayList<>();

        try {
            System.out.println("-------------------");
            System.out.println("SELECT * tariff");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(GET_ALL_TARIFFS);

            while(rs.next()) {
                tariffs.add(tariffMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return tariffs;
    }

    private Tariff tariffMap(ResultSet rs) throws SQLException {
        Tariff tariff = new Tariff();
        tariff.setId(rs.getLong("id"));
        tariff.setTariffName(rs.getString("tariff_name"));
        tariff.setTariffPrice(rs.getDouble("tariff_price"));
        tariff.setTariffInfo(rs.getString("tariff_info"));

        return tariff;
    }

    @Override
    public Tariff getById(Long id) throws SQLException {
        Connection conn = null;
        List<Tariff> tariffs = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            tariffs = getTariffById(id, conn, GET_TARIFF_BY_ID);
            conn.commit();

            return tariffs.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() tariff error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Tariff> getTariffById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Tariff> tariffList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tariffList.add(tariffMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return tariffList;
    }

    @Override
    public List<Tariff> getByName(String pattern) throws SQLException {
        Connection conn = null;
        List<Tariff> tariffs = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            tariffs = getTariffsByName(pattern, conn, GET_TARIFF_BY_NAME);
            conn.commit();

            return tariffs;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() tariff error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Tariff> getTariffsByName(String pattern, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Tariff> tariffList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tariffList.add(tariffMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return tariffList;
    }

    @Override
    public boolean update(Tariff tariff) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_TARIFF);

            pstmt.setString(1, tariff.getTariffName());
            pstmt.setDouble(2, tariff.getTariffPrice());
            pstmt.setString(3, tariff.getTariffInfo());
            pstmt.setLong(4, tariff.getId());

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
    public boolean remove(Tariff tariff) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_TARIFF);
            pstmt.setLong(1, tariff.getId());

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
