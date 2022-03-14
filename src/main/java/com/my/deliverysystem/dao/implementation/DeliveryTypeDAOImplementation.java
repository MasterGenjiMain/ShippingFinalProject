package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.dao.DeliveryTypeDAO;
import com.my.deliverysystem.db.entity.DeliveryType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.deliverysystem.db.DbConstants.*;

public class DeliveryTypeDAOImplementation implements DeliveryTypeDAO {

    private final Logger logger = Logger.getLogger(DeliveryTypeDAOImplementation.class);

    @Override
    public void add(DeliveryType deliveryType) throws SQLException {
        logger.debug("Entered add() deliveryTypeImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            insertNewDeliveryType(deliveryType, conn);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Add() deliveryTypeImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewDeliveryType(DeliveryType deliveryType, Connection conn) throws SQLException {
        logger.debug("Entered insertNewDeliveryType() deliveryTypeImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
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
        logger.debug("Entered getAll() deliveryTypeImpl");
        Connection conn = null;
        List<DeliveryType> allDeliveryTypes;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            allDeliveryTypes = getAllDeliveryTypes(conn);
            conn.commit();

        } catch (SQLException e) {
            logger.error("getAll() deliveryTypeImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allDeliveryTypes;
    }

    private List<DeliveryType> getAllDeliveryTypes(Connection conn) throws SQLException {
        logger.debug("Entered getAllDeliveryTypes() deliveryTypeImpl");
        Statement stmt = null;
        ResultSet rs = null;
        List<DeliveryType> cityList = new ArrayList<>();

        try {

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
        logger.debug("Entered deliveryTypeMap() deliveryTypeImpl");
        DeliveryType deliveryType = new DeliveryType();
        deliveryType.setId(rs.getLong("id"));
        deliveryType.setTypeName(rs.getString("type_name"));

        return deliveryType;
    }

    @Override
    public DeliveryType getById(Long id) throws SQLException {
        logger.debug("Entered getById() deliveryTypeImpl");
        Connection conn = null;
        DeliveryType deliveryType;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            deliveryType = getDeliveryTypeById(id, conn);
            conn.commit();

            return deliveryType;

        } catch (SQLException e) {
            logger.error("getById() deliveryTypeImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private DeliveryType getDeliveryTypeById(Long id, Connection conn) throws SQLException {
        logger.debug("Entered getDeliveryTypeById() deliveryTypeImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DeliveryType deliveryType = null;
        try {
            pstmt = conn.prepareStatement(GET_DELIVERY_TYPE_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                deliveryType = deliveryTypeMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return deliveryType;
    }

    @Override
    public DeliveryType getByName(String pattern) throws SQLException {
        logger.debug("Entered getByName() deliveryTypeImpl");
        Connection conn = null;
        DeliveryType deliveryType;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            deliveryType = getDeliveryTypeByName(pattern, conn);
            conn.commit();

            return deliveryType;

        } catch (SQLException e) {
            logger.error("getByName() deliveryTypeImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private DeliveryType getDeliveryTypeByName(String pattern, Connection conn) throws SQLException {
        logger.debug("Entered getDeliveryTypeByName() deliveryTypeImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DeliveryType deliveryType = null;
        try {
            pstmt = conn.prepareStatement(GET_DELIVERY_TYPE_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                deliveryType = deliveryTypeMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return deliveryType;
    }

    @Override
    public boolean update(DeliveryType deliveryType) {
        logger.debug("Entered update() deliveryTypeImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_DELIVERY_TYPE);

            pstmt.setString(1, deliveryType.getTypeName());
            pstmt.setLong(2, deliveryType.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at update() deliveryTypeImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(DeliveryType deliveryType) {
        logger.debug("Entered remove() deliveryTypeImpl");
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
            logger.error("Exception at remove() deliveryTypeImpl" + e);
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
                logger.error("Exception at close() deliveryTypeImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() deliveryTypeImpl" + e);
            }
        }
    }
}
