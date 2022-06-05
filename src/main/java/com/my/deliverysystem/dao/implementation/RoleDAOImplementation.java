package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.dao.daoInterface.RoleDAO;
import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.Role;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImplementation implements RoleDAO {

    private final Logger logger = Logger.getLogger(RoleDAOImplementation.class);

    @Override
    public void add(Role role) throws SQLException {
        logger.debug("Entered add() roleImpl");
        Connection conn = null;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            insertNewRole(role, conn);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Add() roleImpl: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private void insertNewRole(Role role, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.INSERT_INTO_ROLE, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, role.getRoleName());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    role.setId(rs.getLong(1));
                }
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Role> getAll() throws SQLException {
        logger.debug("Entered getAll() roleImpl");
        Connection conn = null;
        List<Role> allRoles;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");

            conn.setAutoCommit(false);
            allRoles = getAllRoles(conn);
            conn.commit();


        } catch (SQLException e) {
            logger.error("getAll() roleImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }

        return allRoles;
    }

    private List<Role> getAllRoles(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Role> roleList = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(DbConstants.GET_ALL_ROLES);

            while(rs.next()) {
                roleList.add(roleMap(rs));
            }

        } finally {
            close(rs);
            close(stmt);
        }
        return roleList;
    }

    private Role roleMap(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("id"));
        role.setRoleName(rs.getString("role_name"));

        return role;
    }

    @Override
    public Role getById(long id) throws SQLException {
        logger.debug("Entered getById() roleImpl");
        Connection conn = null;
        Role role;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            role = getRoleById(id, conn);
            conn.commit();

            return role;

        } catch (SQLException e) {
            logger.error("getById() roleImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Role getRoleById(Long id, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Role role = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_ROLE_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                role = roleMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return role;
    }

    @Override
    public Role getByName(String pattern) throws SQLException {
        logger.debug("Entered getByName() roleImpl");
        Connection conn = null;
        Role role;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            role = getRoleByName(pattern, conn);
            conn.commit();

            return role;

        } catch (SQLException e) {
            logger.error("getByName() roleImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Role getRoleByName(String pattern, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Role role = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_ROLE_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                role = roleMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return role;
    }

    @Override
    public boolean update(Role role) {
        logger.debug("Entered update() roleImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.UPDATE_ROLE);

            pstmt.setString(1, role.getRoleName());
            pstmt.setLong(2, role.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at update() roleImpl" + e);
        } finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }

    @Override
    public boolean remove(Role role) {
        logger.debug("Entered remove() roleImpl");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DbConstants.DELETE_ROLE);
            pstmt.setLong(1, role.getId());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }

            conn.commit();

        } catch (SQLException e) {
            logger.error("Exception at remove() roleImpl" + e);
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
                logger.error("Exception at close() roleImpl" + e);
            }
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Exception at rollback() roleImpl" + e);
            }
        }
    }
}
