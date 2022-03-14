package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.Cargo;
import com.my.deliverysystem.dao.CargoDAO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.deliverysystem.db.DbConstants.*;

public class CargoDAOImplementation implements CargoDAO {

    private final Logger logger = Logger.getLogger(CargoDAOImplementation.class);

    @Override
    public void add(Cargo cargo) throws SQLException {
        logger.debug("Entered add() cargoImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            insertNewCargo(cargo, conn);
            conn.commit();

        } catch (SQLException e) {
            logger.error("Add() cargo error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewCargo(Cargo cargo, Connection conn) throws SQLException {
        logger.debug("Entered insertNewCargo() cargoImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(INSERT_INTO_CARGO, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cargo.getName());
            pstmt.setString(2, cargo.getDescription());
            pstmt.setLong(3, cargo.getDeliveryOrderId());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    cargo.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }

    }

    @Override
    public List<Cargo> getAll() throws SQLException {
        logger.debug("Entered getAll() cargoImpl");
        Connection conn = null;
        List<Cargo> allCargos;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            allCargos = getAllCargo(conn);
            conn.commit();

        } catch (SQLException e) {
            logger.error("getAll() cargo error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allCargos;
    }

    private List<Cargo> getAllCargo(Connection conn) throws SQLException {
        logger.debug("Entered getAllCargo() cargoImpl");
        Statement stmt = null;
        ResultSet rs = null;
        List<Cargo> cargoList = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(GET_ALL_CARGOS);

            while(rs.next()) {
                cargoList.add(cargoMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return cargoList;
    }

    @Override
    public List<Cargo> getByName(String pattern) throws SQLException {
        logger.debug("Entered getByName() cargoImpl");
        Connection conn = null;
        List<Cargo> cargos;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            cargos = getCargoByName(pattern, conn);
            conn.commit();

            return cargos;

        } catch (SQLException e) {
            logger.error("getById() cargo error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Cargo> getCargoByName(String pattern, Connection conn) throws SQLException {
        logger.debug("Entered getCargoByName() cargoImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Cargo> cargoList = new ArrayList<>();
        try {

            pstmt = conn.prepareStatement(GET_CARGO_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cargoList.add(cargoMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return cargoList;
    }

    @Override
    public Cargo getById(Long id) throws SQLException {
        logger.debug("Entered getById() cargoImpl");
        Connection conn = null;
        Cargo cargo;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            cargo = getCargoById(id, conn);
            conn.commit();

            return cargo;

        } catch (SQLException e) {
            logger.error("getById() cargo error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Cargo getCargoById(Long id, Connection conn) throws SQLException {
        logger.debug("Entered getCargoById() cargoImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cargo cargo = null;

        try {
            pstmt = conn.prepareStatement(GET_CARGO_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cargo = cargoMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return cargo;
    }

    private Cargo cargoMap(ResultSet rs) throws SQLException {
        logger.debug("Entered cargoMap() cargoImpl");
        Cargo cargo = new Cargo();
        cargo.setId(rs.getLong("id"));
        cargo.setName(rs.getString("name"));
        cargo.setDescription(rs.getString("description"));
        cargo.setDeliveryOrderId(rs.getLong("delivery_order_id"));

        return cargo;
    }

    @Override
    public boolean update(Cargo cargo) {
        logger.debug("Entered update() cargoImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_CARGO);

            pstmt.setString(1, cargo.getName());
            pstmt.setString(2, cargo.getDescription());
            pstmt.setLong(3, cargo.getDeliveryOrderId());
            pstmt.setLong(4, cargo.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at update() cargoImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(Cargo cargo) {
        logger.debug("Entered remove() cargoImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_CARGO);
            pstmt.setLong(1, cargo.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at remove() cargoImpl" + e);
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
                logger.error("Exception at close() cargoImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() cargoImpl" + e);
            }
        }
    }
}
