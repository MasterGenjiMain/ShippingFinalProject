package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.dao.daoInterface.UserDAO;
import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImplementation implements UserDAO {

    private final Logger logger = Logger.getLogger(UserDAOImplementation.class.getName());

    @Override
    public void add(User user) throws SQLException {
        logger.debug("Entered add() userImpl");
        Connection conn = null;
        try {
            logger.debug("Connecting to db...");
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected to db!");

            conn.setAutoCommit(false);
            insertNewUser(user, conn);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Add() user error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewUser(User user, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.INSERT_INTO_USER, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
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
        logger.debug("Entered getAll() userImpl");
        Connection conn = null;
        List<User> allUsers;

        try {
            logger.debug("Connecting to db...");
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected to db!");

            conn.setAutoCommit(false);
            allUsers = getAllUsers(conn);
            conn.commit();


        } catch (SQLException e) {
            logger.error("getAll() userImpl error: " + e);
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
            stmt = conn.createStatement();
            rs = stmt.executeQuery(DbConstants.GET_ALL_USERS);

            while(rs.next()) {
                users.add(userMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return users;
    }

    @Override
    public User getById(Long id) throws SQLException {
        logger.debug("Entered getById() userImpl");
        Connection conn = null;
        User user;

        try {
            logger.debug("Connecting...");
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            user = getUserById(id, conn);
            conn.commit();

            return user;

        } catch (SQLException e) {
            logger.error("getById() userImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private User getUserById(Long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_USER_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = userMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return user;
    }

    @Override
    public User getByUsername(String pattern) throws SQLException {
        logger.debug("Entered getByUsername() userImpl");
        Connection conn = null;
        User user;

        try {
            logger.debug("Connecting...");
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            user = getUserByUsername(pattern, conn);
            conn.commit();

            return user;

        } catch (SQLException e) {
            logger.error("getById() userImpl error:" + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private User getUserByUsername(String pattern, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_USER_BY_USERNAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = userMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return user;
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
    public boolean update(User user) {
        logger.debug("Entered update() userImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            logger.debug("Connecting...");
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.UPDATE_USER);
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
            logger.error("Exception at update() userImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(User user) {
        logger.debug("Entered remove() userImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            logger.debug("Connecting...");
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.DELETE_USER);
            pstmt.setLong(1, user.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at remove() userImpl" + e);
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
                logger.error("Exception at close() userImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() userImpl" + e);
            }
        }
    }
}
