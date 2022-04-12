package com.my.deliverysystem.dao.implementation.beanImpl;

import com.my.deliverysystem.dao.daoInterface.beanDAO.DeliveryOrderBeanDAO;
import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.bean.DeliveryOrderBean;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DeliveryOrderBeanDAOImpl implements DeliveryOrderBeanDAO {
    Logger logger = Logger.getLogger(DeliveryOrderBeanDAOImpl.class);

    @Override
    public List<DeliveryOrderBean> getAll() throws SQLException {
        logger.debug("Entered getAll() DeliveryOrderBeanDAOImpl");
        Connection conn = null;
        List<DeliveryOrderBean> deliveryOrderBeans;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            deliveryOrderBeans = getAllDeliveryOrderBrans(conn);
            conn.commit();


        } catch (SQLException e) {
            logger.error("getAll() deliveryOrderImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return deliveryOrderBeans;
    }

    @Override
    public DeliveryOrderBean getById(long id) throws SQLException {
        logger.debug("Entered getByReceiptId() " + getClass().getName());
        Connection conn = null;
        DeliveryOrderBean deliveryOrderBean;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            deliveryOrderBean = getDeliveryOrderById(id, conn);
            conn.commit();

            return deliveryOrderBean;

        } catch (SQLException e) {
            logger.error("getById() locationImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private DeliveryOrderBean getDeliveryOrderById(long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DeliveryOrderBean deliveryOrderBean = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_DELIVERY_ORDER_BEAN_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                deliveryOrderBean = deliveryOrderBeanMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return deliveryOrderBean;
    }

    private List<DeliveryOrderBean> getAllDeliveryOrderBrans(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<DeliveryOrderBean> deliveryOrderBeans = new ArrayList<>();

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(DbConstants.GET_ALL_DELIVERY_ORDER_BEANS);

            while(rs.next()) {
                deliveryOrderBeans.add(deliveryOrderBeanMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return deliveryOrderBeans;
    }

    private DeliveryOrderBean deliveryOrderBeanMap(ResultSet rs) throws SQLException {
        DeliveryOrderBean deliveryOrderBean = new DeliveryOrderBean();
        deliveryOrderBean.setId(rs.getLong("id"));
        deliveryOrderBean.setLocationFrom(rs.getString(2));
        deliveryOrderBean.setLocationTo(rs.getString(3));
        deliveryOrderBean.setCargoName(rs.getString("cargo_name"));
        deliveryOrderBean.setCargoDescription(rs.getString("cargo_description"));
        deliveryOrderBean.setAddress(rs.getString("address"));
        deliveryOrderBean.setDeliveryType(rs.getString("type_name"));
        deliveryOrderBean.setWeight(rs.getDouble("weight"));
        deliveryOrderBean.setVolume(rs.getDouble("volume"));
        deliveryOrderBean.setReceivingDate(rs.getTimestamp("receiving_date"));
        deliveryOrderBean.setTariffName(rs.getString("tariff_name"));

        return deliveryOrderBean;
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
