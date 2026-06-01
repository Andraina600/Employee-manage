package school.hei.employee.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import school.hei.employee.datasource.DataSource;
import school.hei.employee.entity.Intern;

@Repository
public class InternRepository {

  private final DataSource dataSource;

  public InternRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  private Intern map(ResultSet rs) throws SQLException {
    return new Intern(
        rs.getLong("id"),
        rs.getString("firstname"),
        rs.getString("lastname"),
        rs.getString("email"),
        rs.getString("department"),
        rs.getDouble("salary"),
        rs.getBoolean("remunere"),
        rs.getBoolean("actif"),
        rs.getLong("manager_id"));
  }

  public List<Intern> findAll(
      String department,
      Boolean remunere,
      Long managerId,
      int start,
      int end,
      String sort,
      String order) {
    StringBuilder sql =
        new StringBuilder(
            "SELECT id, firstname, lastname, email, department, salary, remunere, actif, manager_id"
                + " FROM interns WHERE 1=1");
    if (department != null) sql.append(" AND department = ?");
    if (remunere != null) sql.append(" AND remunere = ?");
    if (managerId != null) sql.append(" AND manager_id = ?");
    sql.append(" ORDER BY ").append(sort).append(" ").append(order);
    sql.append(" LIMIT ? OFFSET ?");

    List<Intern> result = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      int i = 1;
      if (department != null) ps.setString(i++, department);
      if (remunere != null) ps.setBoolean(i++, remunere);
      if (managerId != null) ps.setLong(i++, managerId);
      ps.setInt(i++, end - start);
      ps.setInt(i++, start);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) result.add(map(rs));
    } catch (SQLException e) {
      throw new RuntimeException("Error fetching interns", e);
    }
    return result;
  }

  public int count(String department, Boolean remunere, Long managerId) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(id) FROM interns WHERE 1=1");
    if (department != null) sql.append(" AND department = ?");
    if (remunere != null) sql.append(" AND remunere = ?");
    if (managerId != null) sql.append(" AND manager_id = ?");

    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql.toString())) {
      int i = 1;
      if (department != null) ps.setString(i++, department);
      if (remunere != null) ps.setBoolean(i++, remunere);
      if (managerId != null) ps.setLong(i++, managerId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) return rs.getInt(1);
    } catch (SQLException e) {
      throw new RuntimeException("Error counting interns", e);
    }
    return 0;
  }

  public Intern findById(Long id) {
    String sql =
        "SELECT id, firstname, lastname, email, department, salary, remunere, actif, manager_id"
            + " FROM interns WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) return map(rs);
    } catch (SQLException e) {
      throw new RuntimeException("Error fetching intern with id: " + id, e);
    }
    return null;
  }

  public Intern save(Intern i) {
    String sql =
        "INSERT INTO interns (firstname, lastname, email, department, salary, remunere, actif,"
            + " manager_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, i.getFirstname());
      ps.setString(2, i.getLastname());
      ps.setString(3, i.getEmail());
      ps.setString(4, i.getDepartment());
      ps.setDouble(5, i.getSalary());
      ps.setBoolean(6, i.getRemunere());
      ps.setBoolean(7, i.getActif());
      ps.setLong(8, i.getManagerId());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) i.setId(rs.getLong("id"));
    } catch (SQLException e) {
      throw new RuntimeException("Error saving intern", e);
    }
    return i;
  }

  public Intern update(Long id, Intern i) {
    String sql =
        "UPDATE interns SET firstname=?, lastname=?, email=?, department=?, salary=?, remunere=?,"
            + " actif=?, manager_id=? WHERE id=?";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, i.getFirstname());
      ps.setString(2, i.getLastname());
      ps.setString(3, i.getEmail());
      ps.setString(4, i.getDepartment());
      ps.setDouble(5, i.getSalary());
      ps.setBoolean(6, i.getRemunere());
      ps.setBoolean(7, i.getActif());
      ps.setLong(8, i.getManagerId());
      ps.setLong(9, id);
      ps.executeUpdate();
      i.setId(id);
    } catch (SQLException e) {
      throw new RuntimeException("Error updating intern with id: " + id, e);
    }
    return i;
  }

  public void delete(Long id) {
    String sql = "DELETE FROM interns WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error deleting intern with id: " + id, e);
    }
  }
}
