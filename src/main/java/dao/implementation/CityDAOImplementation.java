package dao.implementation;

import dao.CityDAO;
import db.DbManager;
import db.entity.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAOImplementation implements CityDAO {

    private static final String INSERT_INTO_CITY = "INSERT INTO city (id, city_name) VALUES (DEFAULT, ?)";
    private static final String GET_ALL_CARGOS = "SELECT id, city_name FROM city ORDER BY city.id";
    private static final String GET_CITY_BY_ID = "SELECT id, city_name FROM city where city.id=? ORDER BY city.id";
    private static final String GET_CITY_BY_NAME = "SELECT id, city_name FROM city where city.city_name=? ORDER BY city.id";
    private static final String UPDATE_CARGO = "UPDATE city SET city_name=? WHERE id=?";
    private static final String DELETE_CITY = "DELETE FROM city WHERE id=?";

    @Override
    public void add(City city) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewCity(city, conn);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() city error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewCity(City city, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            System.out.println("-------------------");
            System.out.println("INSERT INTO city");
            pstmt = conn.prepareStatement(INSERT_INTO_CITY, Statement.RETURN_GENERATED_KEYS);
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
        Connection conn = null;
        List<City> allCities;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allCities = getAllCities(conn);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() city error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allCities;
    }

    private List<City> getAllCities(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<City> cityList = new ArrayList<>();

        try {
            System.out.println("-------------------");
            System.out.println("SELECT * cargo");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(GET_ALL_CARGOS);

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
        Connection conn = null;
        List<City> cities = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            cities = getCityById(id, conn, GET_CITY_BY_ID);
            conn.commit();

            return cities.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() city error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<City> getCityById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<City> cityList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cityList.add(cityMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return cityList;

    }

    @Override
    public List<City> getByName(String pattern) throws SQLException {
        Connection conn = null;
        List<City> cities = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            cities = getCityByName(pattern, conn, GET_CITY_BY_NAME);
            conn.commit();

            return cities;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() city error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<City> getCityByName(String pattern, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<City> cargoList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cargoList.add(cityMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return cargoList;
    }

    @Override
    public boolean update(City city) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_CARGO);

            pstmt.setString(1, city.getCityName());
            pstmt.setLong(2, city.getId());

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
    public boolean remove(City city) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_CITY);
            pstmt.setLong(1, city.getId());

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

    private City cityMap(ResultSet rs) throws SQLException {
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
