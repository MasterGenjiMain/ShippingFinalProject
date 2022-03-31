package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.Location;
import com.my.deliverysystem.dao.daoInterface.LocationDAO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImplementation implements LocationDAO {

    private final Logger logger = Logger.getLogger(LocationDAOImplementation.class);

    @Override
    public void add(Location location) throws SQLException {
        logger.debug("Entered add() locationImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            insertNewLocation(location, conn);
            conn.commit();

        } catch (SQLException e) {
            logger.error("Add() locationImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewLocation(Location location, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(DbConstants.INSERT_INTO_LOCATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, location.getLocationName());
            pstmt.setLong(2, location.getCityId());
            pstmt.setInt(3, location.getIsActive());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    location.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Location> getAll() throws SQLException {
        logger.debug("Entered getAll() locationImpl");
        Connection conn = null;
        List<Location> allLocations;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            allLocations = getAllLocations(conn);
            conn.commit();

        } catch (SQLException e) {
            logger.error("getAll() locationImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allLocations;
    }

    private List<Location> getAllLocations(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Location> locationList = new ArrayList<>();

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(DbConstants.GET_ALL_LOCATIONS);

            while(rs.next()) {
                locationList.add(locationMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return locationList;
    }

    private Location locationMap(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setId(rs.getLong("id"));
        location.setLocationName(rs.getString("location_name"));
        location.setCityId(rs.getLong("city_id"));
        location.setIsActive(rs.getInt("is_active"));

        return location;
    }

    @Override
    public Location getById(Long id) throws SQLException {
        logger.debug("Entered getById() locationImpl");
        Connection conn = null;
        Location location;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            location = getLocationById(id, conn);
            conn.commit();

            return location;

        } catch (SQLException e) {
            logger.error("getById() locationImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Location getLocationById(Long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Location location = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_LOCATION_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                location = locationMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return location;
    }

    @Override
    public Location getByName(String pattern) throws SQLException {
        logger.debug("Entered getByName() locationImpl");
        Connection conn = null;
        Location location;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            location = getLocationByName(pattern, conn);
            conn.commit();

            return location;

        } catch (SQLException e) {
            logger.error("getById() locationImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Location getLocationByName(String pattern, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Location location = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_LOCATION_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                location = locationMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return location;
    }

    @Override
    public boolean update(Location location) {
        logger.debug("Entered update() locationImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.UPDATE_LOCATION);

            pstmt.setString(1, location.getLocationName());
            pstmt.setLong(2, location.getCityId());
            pstmt.setInt(3, location.getIsActive());
            pstmt.setLong(4, location.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at update() locationImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(Location location) {
        logger.debug("Entered remove() locationImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.DELETE_LOCATION);
            pstmt.setLong(1, location.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at remove() locationImpl" + e);
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
