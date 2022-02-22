package db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DbManager {
    private static DbManager instance;
    private DataSource dataSource;

    private DbManager() {
    }

    public static synchronized DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            if (dataSource == null) {
                InitialContext initialContext = new InitialContext();
                dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/mysql");
            }

        } catch (NamingException e) {
           e.printStackTrace();
        }
        return dataSource.getConnection();
    }
}
