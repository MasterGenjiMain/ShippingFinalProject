package dao.implementation;

import dao.RoleDAO;
import db.DbManager;
import db.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImplementation implements RoleDAO {
    private static final String INSERT_INTO_ROLE = "INSERT INTO role (id, role_name) VALUES (DEFAULT, ?)";
    private static final String GET_ALL_ROLES = "SELECT id, role_name FROM role ORDER BY role.id";
    private static final String GET_ROLE_BY_ID = "SELECT id, role_name FROM role WHERE role.id=? ORDER BY role.id";
    private static final String GET_ROLE_BY_NAME = "SELECT id, role_name FROM role WHERE role.role_name=? ORDER BY role.id";
    private static final String UPDATE_ROLE = "UPDATE role SET role_name=? WHERE id=?";
    private static final String DELETE_ROLE = "DELETE FROM role WHERE id=?";

    @Override
    public void add(Role role) throws SQLException {
        Connection conn = null;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            insertNewRole(role, conn);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add() role: " + e.getMessage());
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
            System.out.println("-------------------");
            System.out.println("INSERT INTO role");
            pstmt = conn.prepareStatement(INSERT_INTO_ROLE, Statement.RETURN_GENERATED_KEYS);
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
        Connection conn = null;
        List<Role> allRoles;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");

            conn.setAutoCommit(false);
            allRoles = getAllRoles(conn);
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getAll() role error: " + e.getMessage());
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
            System.out.println("-------------------");
            System.out.println("SELECT * role");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(GET_ALL_ROLES);

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
    public Role getById(Long id) throws SQLException {
        Connection conn = null;
        List<Role> roles = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            roles = getRoleById(id, conn, GET_ROLE_BY_ID);
            conn.commit();

            return roles.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() role error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Role> getRoleById(Long id, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Role> rolesList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rolesList.add(roleMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return rolesList;
    }

    @Override
    public List<Role> getByName(String pattern) throws SQLException {
        Connection conn = null;
        List<Role> roles = null;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);
            roles = getRoleByName(pattern, conn, GET_ROLE_BY_NAME);
            conn.commit();

            return roles;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getById() role error: " + e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private List<Role> getRoleByName(String pattern, Connection conn, String sql) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Role> rolesList = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rolesList.add(roleMap(rs));
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return rolesList;
    }

    @Override
    public boolean update(Role role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(UPDATE_ROLE);

            pstmt.setString(1, role.getRoleName());
            pstmt.setLong(2, role.getId());

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
    public boolean remove(Role role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            System.out.println("Connecting...");
            conn = DbManager.getInstance().getConnection();
            System.out.println("Connected.");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DELETE_ROLE);
            pstmt.setLong(1, role.getId());

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
