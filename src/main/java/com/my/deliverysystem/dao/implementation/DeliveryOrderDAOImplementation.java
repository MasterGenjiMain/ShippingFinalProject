package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.dao.daoInterface.DeliveryOrderDAO;
import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.DeliveryOrder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryOrderDAOImplementation implements DeliveryOrderDAO {

    private final Logger logger = Logger.getLogger(DeliveryOrderDAOImplementation.class);

    @Override
    public void add(DeliveryOrder deliveryOrder) throws SQLException {
        logger.debug("Entered add() deliveryOrderImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            insertNewDeliveryOrder(deliveryOrder, conn);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Add() deliveryOrderImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewDeliveryOrder(DeliveryOrder deliveryOrder, Connection conn) throws SQLException {
        logger.debug("Entered insertNewDeliveryOrder() deliveryOrderImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.INSERT_INTO_DELIVERY_ORDER, Statement.RETURN_GENERATED_KEYS);
            setDeliveryOrder(deliveryOrder, pstmt);
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
        logger.debug("Entered getAll() deliveryOrderImpl");
        Connection conn = null;
        List<DeliveryOrder> deliveryOrders;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            deliveryOrders = getAllDeliveryOrders(conn);
            conn.commit();


        } catch (SQLException e) {
            logger.error("getAll() deliveryOrderImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return deliveryOrders;
    }

    private List<DeliveryOrder> getAllDeliveryOrders(Connection conn) throws SQLException {
        logger.debug("Entered getAllDeliveryOrders() deliveryOrderImpl");
        Statement stmt = null;
        ResultSet rs = null;
        List<DeliveryOrder> deliveryOrders = new ArrayList<>();

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(DbConstants.GET_ALL_DELIVERY_ORDERS);

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
        logger.debug("Entered deliveryOrderMap() deliveryOrderImpl");
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
        logger.debug("Entered getByLocationFromId() deliveryOrderImpl");
        return getDeliveryOrders(id, DbConstants.GET_DELIVERY_ORDER_BY_LOCATION_FROM_ID);
    }

    @Override
    public List<DeliveryOrder> getByLocationToId(Long id) throws SQLException {
        logger.debug("Entered getByLocationToId() deliveryOrderImpl");
        return getDeliveryOrders(id, DbConstants.GER_DELIVERY_ORDER_BY_LOCATION_TO_ID);
    }

    private List<DeliveryOrder> getDeliveryOrders(Long id, String sql) throws SQLException {
        logger.debug("Entered getDeliveryOrders() deliveryOrderImpl");
        Connection conn = null;
        List<DeliveryOrder> deliveryOrders;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            deliveryOrders = getDeliveryOrdersById(id, conn, sql);
            conn.commit();

            return deliveryOrders;

        } catch (SQLException e) {
            logger.error("getByUserId() deliveryOrderImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<DeliveryOrder> getDeliveryOrdersById(Long id, Connection conn, String sql) throws SQLException {
        logger.debug("Entered getDeliveryOrdersById() deliveryOrderImpl");
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
        logger.debug("Entered update() deliveryOrderImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.UPDATE_DELIVERY_ORDER);

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
        logger.debug("Entered setDeliveryOrder() deliveryOrderImpl");
        pstmt.setLong(1, deliveryOrder.getlocationfromid());
        pstmt.setLong(2, deliveryOrder.getLocationToId());
        pstmt.setString(3, deliveryOrder.getAddress());
        pstmt.setLong(4, deliveryOrder.getDeliveryTypeId());
        pstmt.setDouble(5, deliveryOrder.getWeight());
        pstmt.setDouble(6, deliveryOrder.getVolume());
        pstmt.setDate(7, (Date) deliveryOrder.getReceivingDate());
        pstmt.setLong(8, deliveryOrder.getTariffId());
        pstmt.setLong(9, deliveryOrder.getReceiptId());
    }

    @Override
    public boolean remove(DeliveryOrder deliveryOrder) {
        logger.debug("Entered remove() deliveryOrderImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.DELETE_DELIVERY_ORDER);
            pstmt.setLong(1, deliveryOrder.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at remove() deliveryOrderImpl" + e);
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
                logger.error("Exception at close() deliveryOrderImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() deliveryOrderImpl" + e);
            }
        }
    }
}
