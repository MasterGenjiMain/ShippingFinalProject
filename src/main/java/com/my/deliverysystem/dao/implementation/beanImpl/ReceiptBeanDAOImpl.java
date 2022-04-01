package com.my.deliverysystem.dao.implementation.beanImpl;

import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.bean.LocationBean;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptBeanDAOImpl {
    private final Logger logger = Logger.getLogger(ReceiptBeanDAOImpl.class);

    public List<ReceiptBean> getByUserId(Long id) throws SQLException {
        logger.debug("Entered getByUserId() receiptImpl");
        return getReceipts(id, DbConstants.GET_RECEIPTS_BEAN_BY_ID);
    }

    private List<ReceiptBean> getReceipts(Long id, String sql) throws SQLException {
        Connection conn = null;
        List<ReceiptBean> receiptBeans;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            receiptBeans = getReceiptsById(id, conn, sql);
            conn.commit();

            return receiptBeans;

        } catch (SQLException e) {
            logger.error("getByUserId() receiptImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<ReceiptBean> getReceiptsById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReceiptBean> receiptList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                receiptList.add(receiptMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return receiptList;
    }

    private ReceiptBean receiptMap(ResultSet rs) throws SQLException {
        ReceiptBean receiptBean = new ReceiptBean();
        receiptBean.setId(rs.getLong("id"));
        receiptBean.setUserId(rs.getLong("user_id"));
        receiptBean.setManagerName(rs.getString("username"));
        receiptBean.setPrice(rs.getDouble("price"));
        receiptBean.setReceiptStatusName(rs.getString("status_name"));
        receiptBean.setDeliveryOrderId(rs.getLong("delivery_order_id"));

        return receiptBean;
    }

    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
                autoCloseable = null;
            } catch (Exception e) {
                logger.error("Exception at close() locationImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() locationImpl" + e);
            }
        }
    }
}
