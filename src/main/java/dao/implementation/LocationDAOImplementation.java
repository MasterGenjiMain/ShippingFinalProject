package dao.implementation;

import dao.LocationDAO;
import db.DbManager;
import db.entity.City;
import db.entity.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImplementation implements LocationDAO {
    private static final String INSERT_INTO_LOCATION = "INSERT INTO location (id, location_name, city_id, is_active) VALUES (DEFAULT, ?, ?, ?)";
    private static final String GET_ALL_LOCATIONS = "SELECT id, location_name, city_id, is_active FROM location ORDER BY location.id";
    private static final String GET_LOCATION_BY_ID = "SELECT id, location_name, city_id, is_active FROM location WHERE location.id=? ORDER BY location.id";
    private static final String GET_LOCATION_BY_NAME = "SELECT id, location_name, city_id, is_active FROM location WHERE location.location_name=? ORDER BY location.id";
    private static final String UPDATE_LOCATION = "UPDATE location SET location_name=?, city_id=?, is_active=? WHERE id=?";
    private static final String DELETE_LOCATION = "DELETE FROM location WHERE id=?";

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
            pstmt = conn.prepareStatement(INSERT_INTO_LOCATION, Statement.RETURN_GENERATED_KEYS);
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
            rs = stmt.executeQuery(GET_ALL_LOCATIONS);

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
        List<Location> locations = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            locations = getLocationById(id, conn, GET_LOCATION_BY_ID);
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

    private List<Location> getLocationById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Location> locationList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
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
        List<Location> locations = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            locations = getLocationByName(pattern, conn, GET_LOCATION_BY_NAME);
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

    private List<Location> getLocationByName(String pattern, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Location> locationList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
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

            pstmt = conn.prepareStatement(UPDATE_LOCATION);

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

            pstmt = conn.prepareStatement(DELETE_LOCATION);
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
