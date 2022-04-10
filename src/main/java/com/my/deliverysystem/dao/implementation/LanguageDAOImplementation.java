package com.my.deliverysystem.dao.implementation;

import com.my.deliverysystem.dao.daoInterface.LanguageDAO;
import com.my.deliverysystem.db.DbConstants;
import com.my.deliverysystem.db.DbManager;
import com.my.deliverysystem.db.entity.Language;
import com.my.deliverysystem.db.entity.Role;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageDAOImplementation implements LanguageDAO {
    Logger logger = Logger.getLogger(LanguageDAOImplementation.class);

    @Override
    public Language getByName(String pattern) throws SQLException {
        logger.debug("Entered getByName() roleImpl");
        Connection conn = null;
        Language language;

        try {
            conn = DbManager.getInstance().getConnection();
            logger.debug("Connected!");
            conn.setAutoCommit(false);
            language = getLanguageByName(pattern, conn);
            conn.commit();

            return language;

        } catch (SQLException e) {
            logger.error("getByName() roleImpl error: " + e);
            rollback(conn);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Language getLanguageByName(String pattern, Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Language language = null;
        try {
            pstmt = conn.prepareStatement(DbConstants.GET_LANGUAGE_BY_NAME);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                language = languageMap(rs);
            }

        } finally {
            close(rs);
            close(pstmt);
        }
        return language;
    }

    private Language languageMap(ResultSet rs) throws SQLException {
        Language language = new Language();
        language.setId(rs.getLong("id"));
        language.setLanguageName(rs.getString("language_name"));

        return language;
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
