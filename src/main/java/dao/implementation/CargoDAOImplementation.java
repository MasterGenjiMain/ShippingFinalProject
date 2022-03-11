package dao.implementation;

import db.DbManager;
import db.entity.Cargo;
import dao.CargoDAO;
import db.entity.DeliveryOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.DbConstants.*;

public class CargoDAOImplementation implements CargoDAO {



    @Override
    public void add(Cargo cargo, DeliveryOrder deliveryOrder) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewCargo(cargo, deliveryOrder, conn);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() cargo error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewCargo(Cargo cargo, DeliveryOrder deliveryOrder, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            System.out.println("-------------------");
            System.out.println("INSERT INTO cargo");
            pstmt = conn.prepareStatement(INSERT_INTO_CARGO, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cargo.getName());
            pstmt.setString(2, cargo.getDescription());
            pstmt.setLong(3, deliveryOrder.getId());
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
        Connection conn = null;
        List<Cargo> allCargos;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allCargos = getAllCargo(conn);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() cargo error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allCargos;
    }

    private List<Cargo> getAllCargo(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Cargo> cargoList = new ArrayList<>();

        try {
            System.out.println("-------------------");
            System.out.println("SELECT * cargo");

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
        Connection conn = null;
        List<Cargo> cargos;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            cargos = getCargoByName(pattern, conn);
            conn.commit();

            return cargos;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() cargo error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Cargo> getCargoByName(String pattern, Connection conn) throws SQLException {
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
        Connection conn = null;
        List<Cargo> cargos;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            cargos = getCargoById(id, conn);
            conn.commit();

            return cargos.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() cargo error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Cargo> getCargoById(Long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Cargo> cargoList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(GET_CARGO_BY_ID);
            pstmt.setLong(1, id);
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

    private Cargo cargoMap(ResultSet rs) throws SQLException {
        Cargo cargo = new Cargo();
        cargo.setId(rs.getLong("id"));
        cargo.setName(rs.getString("name"));
        cargo.setDescription(rs.getString("description"));
        cargo.setDeliveryOrderId(rs.getLong("delivery_order_id"));

        return cargo;
    }

    @Override
    public boolean update(Cargo cargo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
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
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(Cargo cargo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_CARGO);
            pstmt.setLong(1, cargo.getId());

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
