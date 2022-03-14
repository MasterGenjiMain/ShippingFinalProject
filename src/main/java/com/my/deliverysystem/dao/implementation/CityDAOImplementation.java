package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.dao.CityDAO;
import com.my.deliverysystem.db.entity.City;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAOImplementation implements CityDAO {

    private final Logger logger = Logger.getLogger(CargoDAOImplementation.class);

    @Override
    public void add(City city) throws SQLException {
        logger.debug("Entered add() cityImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected");

            conn.setAutoCommit(false);
            insertNewCity(city, conn);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Add() cityImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewCity(City city, Connection conn) throws SQLException {
        logger.debug("Entered insertNewCity() cityImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.INSERT_INTO_CITY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, city.getCityName());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    city.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<City> getAll() throws SQLException {
        logger.debug("Entered getAll() cityImpl");
        Connection conn = null;
        List<City> allCities;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected");

            conn.setAutoCommit(false);
            allCities = getAllCities(conn);
            conn.commit();


        } catch (SQLException e) {
            logger.error("getAll() cityImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allCities;
    }

    private List<City> getAllCities(Connection conn) throws SQLException {
        logger.debug("Entered getAllCities() cityImpl");
        Statement stmt = null;
        ResultSet rs = null;
        List<City> cityList = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(DbConstants.GET_ALL_CITIES);

            while(rs.next()) {
                cityList.add(cityMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return cityList;
    }

    @Override
    public City getById(Long id) throws SQLException {
        logger.debug("Entered getById() cityImpl");
        Connection conn = null;
        City city;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            city = getCityById(id, conn);
            conn.commit();

            return city;

        } catch (SQLException e) {
            logger.error("getById() cityImpl error: " + e);

            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private City getCityById(Long id, Connection conn) throws SQLException {
        logger.debug("Entered getCityById() cityImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        City city = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_CITY_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                city = cityMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return city;

    }

    @Override
    public City getByName(String pattern) throws SQLException {
        logger.debug("Entered getByName() cityImpl");
        Connection conn = null;
        City city;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            city = getCityByName(pattern, conn);
            conn.commit();

            return city;

        } catch (SQLException e) {
            logger.error("getById() cityImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private City getCityByName(String pattern, Connection conn) throws SQLException {
        logger.debug("Entered getCityByName() cityImpl");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        City city = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_CITY_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                city = cityMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return city;
    }

    @Override
    public boolean update(City city) {
        logger.debug("Entered update() cityImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.UPDATE_CITY);

            pstmt.setString(1, city.getCityName());
            pstmt.setLong(2, city.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at update() cityImpl" + e);
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(City city) {
        logger.debug("Entered remove() cityImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.DELETE_CITY);
            pstmt.setLong(1, city.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at remove() cityImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    private City cityMap(ResultSet rs) throws SQLException {
        logger.debug("Entered cityMap() cityImpl");
        City city = new City();
        city.setId(rs.getLong("id"));
        city.setCityName(rs.getString("city_name"));

        return city;
    }

    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
                autoCloseable = null;
            } catch (Exception e) {
                logger.error("Exception at cityMap() cityImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() cityImpl" + e);
            }
        }
    }
}
