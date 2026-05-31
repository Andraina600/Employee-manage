package school.hei.employee.repository;

import school.hei.employee.datasource.DataSource;
import school.hei.employee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

  private final DataSource dataSource;

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
            rs.getBoolean("actif")
    );
  }

  public List<Employee> findAll(String department, Boolean actif, int start, int end, String sort, String order) {
    StringBuilder sql = new StringBuilder(
            "SELECT id, firstname, lastname, email, department, salary, actif FROM employees WHERE 1=1"
    );
    if (department != null) sql.append(" AND department = ?");
    if (actif != null)      sql.append(" AND actif = ?");
    sql.append(" ORDER BY ").append(sort).append(" ").append(order);
    sql.append(" LIMIT ? OFFSET ?");

    List<Employee> result = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      int i = 1;
      if (department != null) ps.setString(i++, department);
      if (actif != null)      ps.setBoolean(i++, actif);
      ps.setInt(i++, end - start);
      ps.setInt(i++, start);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) result.add(map(rs));
    } catch (SQLException e) {
      throw new RuntimeException("Error fetching employees", e);
    }
    return result;
  }

  public int count(String department, Boolean actif) {
    StringBuilder sql = new StringBuilder(
            "SELECT COUNT(id) FROM employees WHERE 1=1"
    );
    if (department != null) sql.append(" AND department = ?");
    if (actif != null)      sql.append(" AND actif = ?");

    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      int i = 1;
      if (department != null) ps.setString(i++, department);
      if (actif != null)      ps.setBoolean(i++, actif);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) return rs.getInt(1);
    } catch (SQLException e) {
      throw new RuntimeException("Error counting employees", e);
    }
    return 0;
  }

  public Employee findById(Long id) {
    String sql = "SELECT id, firstname, lastname, email, department, salary, actif FROM employees WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) return map(rs);
    } catch (SQLException e) {
      throw new RuntimeException("Error fetching employee with id: " + id, e);
    }
    return null;
  }

  public Employee save(Employee e) {
    String sql = "INSERT INTO employees (firstname, lastname, email, department, salary, actif) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
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
    } catch (SQLException ex) {
      throw new RuntimeException("Error saving employee", ex);
    }
    return e;
  }

  public Employee update(Long id, Employee e) {
    String sql = "UPDATE employees SET firstname=?, lastname=?, email=?, department=?, salary=?, actif=? WHERE id=?";
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
    } catch (SQLException ex) {
      throw new RuntimeException("Error updating employee with id: " + id, ex);
    }
    return e;
  }

  public void delete(Long id) {
    String sql = "DELETE FROM employees WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error deleting employee with id: " + id, e);
    }
  }
}