package dao.implementation;

import dao.DeliveryOrderDAO;
import db.DbManager;
import db.entity.DeliveryOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAOImplementation implements DeliveryOrderDAO {
    private static final String INSERT_INTO_DELIVERY_ORDER = "INSERT INTO delivery_order (id, location_from_id," +
            " location_to_id, address, delivery_type_id, weight, volume, receiving_date, tariff_id, receipt_id)" +
            " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_DELIVERY_ORDERS = "SELECT id, location_from_id, location_to_id, address," +
            " delivery_type_id, weight, volume, receiving_date, tariff_id, receipt_id FROM delivery_order ORDER BY delivery_order.id";
    private static final String GET_DELIVERY_ORDER_BY_LOCATION_FROM_ID = "SELECT id, location_from_id, location_to_id," +
            " address,  delivery_type_id, weight, volume, receiving_date, tariff_id, receipt_id FROM delivery_order" +
            " WHERE delivery_order.location_from_id=? ORDER BY delivery_order.id";
    private static final String GER_DELIVERY_ORDER_BY_LOCATION_TO_ID = "SELECT id, location_from_id, location_to_id," +
            " address,  delivery_type_id, weight, volume, receiving_date, tariff_id, receipt_id" +
            " FROM delivery_order WHERE delivery_order.location_to_id=? ORDER BY delivery_order.id";
    private static final String UPDATE_DELIVERY_ORDER = "UPDATE delivery_order SET location_from_id=?," +
            " location_to_id=?, address=?, delivery_type_id=?, weight=?, volume=?, receiving_date=?, tariff_id=?, receipt_id=? WHERE id=?";
    private static final String DELETE_DELIVERY_ORDER = "DELETE FROM delivery_order WHERE id=?";


    @Override
    public void add(DeliveryOrder deliveryOrder) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewDeliveryOrder(deliveryOrder, conn);
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

    private void insertNewDeliveryOrder(DeliveryOrder deliveryOrder, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            System.out.println("-------------------");
            System.out.println("INSERT INTO delivery_order");
            pstmt = conn.prepareStatement(INSERT_INTO_DELIVERY_ORDER, Statement.RETURN_GENERATED_KEYS);
            setDeliveryOrder(deliveryOrder, pstmt);
            pstmt.setLong(8, deliveryOrder.getReceiptId());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    deliveryOrder.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<DeliveryOrder> getAll() throws SQLException {
        Connection conn = null;
        List<DeliveryOrder> deliveryOrders;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            deliveryOrders = getAllDeliveryOrders(conn);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() delivery_order error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return deliveryOrders;
    }

    private List<DeliveryOrder> getAllDeliveryOrders(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<DeliveryOrder> deliveryOrders = new ArrayList<>();

        try {
            System.out.println("-------------------");
            System.out.println("SELECT * delivery_order");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(GET_ALL_DELIVERY_ORDERS);

            while(rs.next()) {
                deliveryOrders.add(deliveryOrderMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return deliveryOrders;
    }

    private DeliveryOrder deliveryOrderMap(ResultSet rs) throws SQLException {
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setId(rs.getLong("id"));
        deliveryOrder.setLocationFromID(rs.getLong("location_from_id"));
        deliveryOrder.setLocationToId(rs.getLong("location_to_id"));
        deliveryOrder.setAddress(rs.getString("address"));
        deliveryOrder.setDeliveryTypeId(rs.getLong("delivery_type_id"));
        deliveryOrder.setWeight(rs.getDouble("weight"));
        deliveryOrder.setVolume(rs.getDouble("volume"));
        deliveryOrder.setReceivingDate(rs.getDate("receiving_date"));
        deliveryOrder.setTariffId(rs.getLong("tariff_id"));
        deliveryOrder.setReceiptId(rs.getLong("receipt_id"));

        return deliveryOrder;
    }

    @Override
    public List<DeliveryOrder> getByLocationFromId(Long id) throws SQLException {
        return getDeliveryOrders(id, GET_DELIVERY_ORDER_BY_LOCATION_FROM_ID);
    }

    @Override
    public List<DeliveryOrder> getByLocationToId(Long id) throws SQLException {
        return getDeliveryOrders(id, GER_DELIVERY_ORDER_BY_LOCATION_TO_ID);
    }

    private List<DeliveryOrder> getDeliveryOrders(Long id, String sql) throws SQLException {
        Connection conn = null;
        List<DeliveryOrder> deliveryOrders = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            deliveryOrders = getDeliveryOrdersById(id, conn, sql);
            conn.commit();

            return deliveryOrders;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getByUserId() delivery_order error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<DeliveryOrder> getDeliveryOrdersById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DeliveryOrder> deliveryOrdersList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                deliveryOrdersList.add(deliveryOrderMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return deliveryOrdersList;
    }

    @Override
    public boolean update(DeliveryOrder deliveryOrder) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_DELIVERY_ORDER);

            setDeliveryOrder(deliveryOrder, pstmt);
            pstmt.setLong(9, deliveryOrder.getReceiptId());
            pstmt.setLong(10, deliveryOrder.getId());

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

    private void setDeliveryOrder(DeliveryOrder deliveryOrder, PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, deliveryOrder.getlocationfromid());
        pstmt.setLong(2, deliveryOrder.getLocationToId());
        pstmt.setString(3, deliveryOrder.getAddress());
        pstmt.setLong(4, deliveryOrder.getDeliveryTypeId());
        pstmt.setDouble(5, deliveryOrder.getWeight());
        pstmt.setDouble(6, deliveryOrder.getVolume());
        pstmt.setDate(7, (Date) deliveryOrder.getReceivingDate());
        pstmt.setLong(8, deliveryOrder.getTariffId());
    }

    @Override
    public boolean remove(DeliveryOrder deliveryOrder) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_DELIVERY_ORDER);
            pstmt.setLong(1, deliveryOrder.getId());

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
