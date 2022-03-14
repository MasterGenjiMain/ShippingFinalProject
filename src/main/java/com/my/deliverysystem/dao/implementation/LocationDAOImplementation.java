package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.Location;
import com.my.deliverysystem.dao.LocationDAO;
import com.my.deliverysystem.db.entity.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImplementation implements LocationDAO {

    @Override
    public void add(Location location, City city) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewLocation(location, city, conn);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() location: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewLocation(Location location, City city, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            System.out.println("-------------------");
            System.out.println("INSERT INTO location");
            pstmt = conn.prepareStatement(DbConstants.INSERT_INTO_LOCATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, location.getLocationName());
            pstmt.setLong(2, city.getId());
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
        Connection conn = null;
        List<Location> allLocations;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allLocations = getAllLocations(conn);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() location: " + e.getMessage());
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
            System.out.println("-------------------");
            System.out.println("SELECT * location");

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
        Connection conn = null;
        List<Location> locations;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            locations = getLocationById(id, conn);
            conn.commit();

            return locations.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() location: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Location> getLocationById(Long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Location> locationList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_LOCATION_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                locationList.add(locationMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return locationList;
    }

    @Override
    public List<Location> getByName(String pattern) throws SQLException {
        Connection conn = null;
        List<Location> locations;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            locations = getLocationByName(pattern, conn);
            conn.commit();

            return locations;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() location: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Location> getLocationByName(String pattern, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Location> locationList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_LOCATION_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                locationList.add(locationMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return locationList;
    }

    @Override
    public boolean update(Location location) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
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
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(Location location) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.DELETE_LOCATION);
            pstmt.setLong(1, location.getId());

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
