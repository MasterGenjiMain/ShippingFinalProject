package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.dao.daoInterface.TariffDAO;
import com.my.deliverysystem.db.entity.Tariff;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.deliverysystem.db.DbConstants.*;

public class TariffDAOImplementation implements TariffDAO {

    private final Logger logger = Logger.getLogger(TariffDAOImplementation.class);

    @Override
    public void add(Tariff tariff) throws SQLException {
        logger.debug("Entered add() tariffImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            insertNewTariff(tariff, conn);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Add() tariffImpl error: " + e);
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
        logger.debug("Entered getAll() tariffImpl");
        Connection conn = null;
        List<Tariff> allTariffs;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            allTariffs = getAllTariffs(conn);
            conn.commit();


        } catch (SQLException e) {
            logger.error("getAll() tariffImpl error: " + e);
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
        logger.debug("Entered getById() tariffImpl");
        Connection conn = null;
        Tariff tariff;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            tariff = getTariffById(id, conn);
            conn.commit();

            return tariff;

        } catch (SQLException e) {
            logger.error("getById() tariffImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Tariff getTariffById(Long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Tariff tariff = null;
        try {
            pstmt = conn.prepareStatement(GET_TARIFF_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                tariff = tariffMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return tariff;
    }

    @Override
    public Tariff getByName(String pattern) throws SQLException {
        logger.debug("Entered getByName() tariffImpl");
        Connection conn = null;
        Tariff tariff;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            tariff = getTariffByName(pattern, conn);
            conn.commit();

            return tariff;

        } catch (SQLException e) {
            logger.error("getByName() tariff error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Tariff getTariffByName(String pattern, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Tariff tariff = null;
        try {
            pstmt = conn.prepareStatement(GET_TARIFF_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tariff = tariffMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return tariff;
    }

    @Override
    public boolean update(Tariff tariff) {
        logger.debug("Entered update() tariffImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
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
            logger.error("Exception at update() userImpl" + e);
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(Tariff tariff) {
        logger.debug("Entered remove() tariffImpl");
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
            logger.error("Exception at remove() userImpl" + e);
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
                logger.error("Exception at close() userImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() userImpl" + e);
            }
        }
    }
}
