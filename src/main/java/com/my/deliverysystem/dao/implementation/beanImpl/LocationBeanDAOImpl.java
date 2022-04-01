package com.my.deliverysystem.dao.implementation.beanImpl;

import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.bean.LocationBean;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationBeanDAOImpl {
    private final Logger logger = Logger.getLogger(LocationBeanDAOImpl.class);

    public List<LocationBean> getAll() throws SQLException {
        logger.debug("Entered getAll() locationImpl");
        Connection conn = null;
        List<LocationBean> allLocationsBeans;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            allLocationsBeans = getAllLocations(conn);
            conn.commit();

        } catch (SQLException e) {
            logger.error("getAll() locationImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allLocationsBeans;
    }

    private List<LocationBean> getAllLocations(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<LocationBean> locationBeanList = new ArrayList<>();

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(DbConstants.GET_ALL_LOCATIONS_BEAN);

            while(rs.next()) {
                locationBeanList.add(locationMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return locationBeanList;
    }

    private LocationBean locationMap(ResultSet rs) throws SQLException {
        LocationBean locationBean = new LocationBean();
        locationBean.setId(rs.getLong("id"));
        locationBean.setLocationName(rs.getString("location_name"));
        locationBean.setCityName(rs.getString("city_name"));
        locationBean.setActiveStatus(rs.getString("active_status_name"));

        return locationBean;
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
