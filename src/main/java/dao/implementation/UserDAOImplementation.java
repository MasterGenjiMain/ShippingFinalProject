package dao.implementation;

import dao.UserDAO;
import db.DbManager;
import db.entity.Role;
import db.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImplementation implements UserDAO {
    private static final String INSERT_INTO_TARIFF = "INSERT INTO user (id, username, email, password, create_time, role_id) VALUES (DEFAULT, ?, ?, ?, DEFAULT, ?)";
    private static final String GET_ALL_USERS = "SELECT id, username, email, password, create_time, role_id FROM user ORDER BY user.id";
    private static final String GET_USER_BY_ID = "SELECT id, username, email, password, create_time, role_id FROM user WHERE user.id=? ORDER BY user.id";
    private static final String GET_USER_BY_USERNAME = "SELECT id, username, email, password, create_time, role_id FROM user WHERE user.username=? ORDER BY user.id";
    private static final String UPDATE_USER = "UPDATE user SET username=?, email=?, password=?, role_id=? WHERE id=?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id=?";

    @Override
    public void add(User user, Role role) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewUser(user, role, conn);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() tariff error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewUser(User user, Role role, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            System.out.println("-------------------");
            System.out.println("INSERT INTO user");
            pstmt = conn.prepareStatement(INSERT_INTO_TARIFF, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getUsername());
            pstmt.setLong(4, role.getId());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        Connection conn = null;
        List<User> allUsers;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allUsers = getAllUsers(conn);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() user error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allUsers;
    }

    private List<User> getAllUsers(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            System.out.println("-------------------");
            System.out.println("SELECT * user");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(GET_ALL_USERS);

            while(rs.next()) {
                users.add(userMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return users;
    }

    private User userMap(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreateTime(rs.getDate("create_time"));
        user.setRoleId(rs.getLong("role_id"));

        return user;
    }

    @Override
    public User getById(Long id) throws SQLException {
        Connection conn = null;
        List<User> users = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            users = getUserById(id, conn, GET_USER_BY_ID);
            conn.commit();

            return users.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() user error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<User> getUserById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userList.add(userMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return userList;
    }

    @Override
    public List<User> getByUsername(String pattern) throws SQLException {
        Connection conn = null;
        List<User> users = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            users = getUsersByUsername(pattern, conn, GET_USER_BY_USERNAME);
            conn.commit();

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() tariff error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<User> getUsersByUsername(String pattern, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userList.add(userMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return userList;
    }

    @Override
    public boolean update(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_USER);

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setLong(4, user.getRoleId());
            pstmt.setLong(5, user.getId());

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
    public boolean remove(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_USER);
            pstmt.setLong(1, user.getId());

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
