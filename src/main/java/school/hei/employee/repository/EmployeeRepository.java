package school.hei.employee.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import school.hei.employee.datasource.DataSource;
import school.hei.employee.entity.Employee;

@Repository
public class EmployeeRepository {
  private static DataSource dataSource;

  public EmployeeRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  private Employee map(ResultSet rs) throws SQLException {
    return new Employee(
        rs.getLong("id"),
        rs.getString("firstname"),
        rs.getString("lastname"),
        rs.getString("email"),
        rs.getString("department"),
        rs.getDouble("salary"),
        rs.getBoolean("actif"));
  }

  public List<Employee> findAll(String department, Boolean actif) throws SQLException {
    StringBuilder sql =
        new StringBuilder(
            "SELECT id, firstname, lastname, email, department, salary, actif FROM employees WHERE"
                + " 1=1");
    if (department != null) sql.append(" AND department = ?");
    if (actif != null) sql.append(" AND actif = ?");

    List<Employee> result = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql.toString())) {

      int i = 1;
      if (department != null) ps.setString(i++, department);
      if (actif != null) ps.setBoolean(i++, actif);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) result.add(map(rs));
    }
    return result;
  }

  public Employee findById(Long id) throws SQLException {
    String sql =
        "SELECT id, firstname, lastname, email, department, salary, actif FROM employees WHERE id ="
            + " ?";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) return map(rs);
    }
    return null;
  }

  public Employee save(Employee e) throws SQLException {
    String sql =
        "INSERT INTO employees (firstname, lastname, email, department, salary, actif) VALUES (?,"
            + " ?, ?, ?, ?, ?) RETURNING id";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, e.getFirstname());
      ps.setString(2, e.getLastname());
      ps.setString(3, e.getEmail());
      ps.setString(4, e.getDepartment());
      ps.setDouble(5, e.getSalary());
      ps.setBoolean(6, e.getActif());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) e.setId(rs.getLong("id"));
    }
    return e;
  }

  public Employee update(Long id, Employee e) throws SQLException {
    String sql =
        "UPDATE employees SET firstname=?, lastname=?, email=?, department=?, salary=?, actif=?"
            + " WHERE id=?";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, e.getFirstname());
      ps.setString(2, e.getLastname());
      ps.setString(3, e.getEmail());
      ps.setString(4, e.getDepartment());
      ps.setDouble(5, e.getSalary());
      ps.setBoolean(6, e.getActif());
      ps.setLong(7, id);
      ps.executeUpdate();
      e.setId(id);
    }
    return e;
  }

  public void delete(Long id) throws SQLException {
    String sql = "DELETE FROM employees WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    }
  }
}
