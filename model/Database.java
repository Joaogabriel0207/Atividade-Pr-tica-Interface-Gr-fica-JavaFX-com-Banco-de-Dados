package model;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:funcionarios.db";
    private static ConnectionSource connectionSource;

    public static ConnectionSource getConnection() throws SQLException {
        if (connectionSource == null) {
            connectionSource = new JdbcConnectionSource(URL);
        }
        return connectionSource;
    }

    public static void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
                connectionSource = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}