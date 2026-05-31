package school.hei.employee.service;

import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;
import school.hei.employee.entity.Employee;
import school.hei.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {
  private final EmployeeRepository repository;

  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  public List<Employee> findAll(
      String department, Boolean actif, int start, int end, String sort, String order)
      throws SQLException {
    return repository.findAll(department, actif, start, end, sort, order);
  }

  public int count(String department, Boolean actif) throws SQLException {
    return repository.count(department, actif);
  }

  public Employee findById(Long id) throws SQLException {
    return repository.findById(id);
  }

  public Employee save(Employee employee) throws SQLException {
    return repository.save(employee);
  }

  public Employee update(Long id, Employee employee) throws SQLException {
    return repository.update(id, employee);
  }

  public void delete(Long id) throws SQLException {
    repository.delete(id);
  }
}
