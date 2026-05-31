package school.hei.employee.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    System.getenv("JDBC_URL"),
                    System.getenv("USERNAME"),
                    System.getenv("PASSWORD")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error", e);
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
