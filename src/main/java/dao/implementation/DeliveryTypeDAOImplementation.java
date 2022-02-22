package dao.implementation;

import dao.DeliveryTypeDAO;
import db.DbManager;
import db.entity.DeliveryType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryTypeDAOImplementation implements DeliveryTypeDAO {
    private static final String INSERT_INTO_DELIVERY_TYPE = "INSERT INTO delivery_type (id, type_name) VALUES (DEFAULT, ?)";
    private static final String GET_ALL_DELIVERY_TYPES = "SELECT id, type_name FROM delivery_type ORDER BY delivery_type.id";
    private static final String GET_DELIVERY_TYPE_BY_ID = "SELECT id, type_name FROM delivery_type where delivery_type.id=? ORDER BY delivery_type.id";
    private static final String GET_DELIVERY_TYPE_BY_NAME = "SELECT id, type_name FROM delivery_type where delivery_type.type_name=? ORDER BY delivery_type.id";
    private static final String UPDATE_DELIVERY_TYPE = "UPDATE delivery_type SET type_name=? WHERE id=?";
    private static final String DELETE_DELIVERY_TYPE = "DELETE FROM delivery_type WHERE id=?";

    @Override
    public void add(DeliveryType deliveryType) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewDeliveryType(deliveryType, conn);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() delivery type: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewDeliveryType(DeliveryType deliveryType, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            System.out.println("-------------------");
            System.out.println("INSERT INTO delivery_type");
            pstmt = conn.prepareStatement(INSERT_INTO_DELIVERY_TYPE, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, deliveryType.getTypeName());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    deliveryType.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<DeliveryType> getAll() throws SQLException {
        Connection conn = null;
        List<DeliveryType> allDeliveryTypes;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allDeliveryTypes = getAllDeliveryTypes(conn);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() delivery_type error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allDeliveryTypes;
    }

    private List<DeliveryType> getAllDeliveryTypes(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<DeliveryType> cityList = new ArrayList<>();

        try {
            System.out.println("-------------------");
            System.out.println("SELECT * delivery_type");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(GET_ALL_DELIVERY_TYPES);

            while(rs.next()) {
                cityList.add(deliveryTypeMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return cityList;
    }

    private DeliveryType deliveryTypeMap(ResultSet rs) throws SQLException {
        DeliveryType deliveryType = new DeliveryType();
        deliveryType.setId(rs.getLong("id"));
        deliveryType.setTypeName(rs.getString("type_name"));

        return deliveryType;
    }

    @Override
    public DeliveryType getById(Long id) throws SQLException {
        Connection conn = null;
        List<DeliveryType> deliveryTypes = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            deliveryTypes = getDeliveryTypeById(id, conn, GET_DELIVERY_TYPE_BY_ID);
            conn.commit();

            return deliveryTypes.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() delivery_type error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<DeliveryType> getDeliveryTypeById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DeliveryType> deliveryTypesList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                deliveryTypesList.add(deliveryTypeMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return deliveryTypesList;
    }

    @Override
    public List<DeliveryType> getByName(String pattern) throws SQLException {
        Connection conn = null;
        List<DeliveryType> deliveryTypes = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            deliveryTypes = getDeliveryTypeByName(pattern, conn, GET_DELIVERY_TYPE_BY_NAME);
            conn.commit();

            return deliveryTypes;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() delivery_type error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<DeliveryType> getDeliveryTypeByName(String pattern, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DeliveryType> deliveryTypesList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                deliveryTypesList.add(deliveryTypeMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return deliveryTypesList;
    }

    @Override
    public boolean update(DeliveryType deliveryType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_DELIVERY_TYPE);

            pstmt.setString(1, deliveryType.getTypeName());
            pstmt.setLong(2, deliveryType.getId());

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
    public boolean remove(DeliveryType deliveryType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_DELIVERY_TYPE);
            pstmt.setLong(1, deliveryType.getId());

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
