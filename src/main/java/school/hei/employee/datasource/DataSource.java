package school.hei.employee.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class DataSource {
  public Connection getConnection() {
    try {
      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection(
          System.getenv("JDBC_URL"), System.getenv("USERNAME"), System.getenv("PASSWORD"));
    } catch (SQLException e) {
      throw new RuntimeException("Database connection error", e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("PostgreSQL driver not found", e);
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
