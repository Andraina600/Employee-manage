package school.hei.employee.service;

import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;
import school.hei.employee.entity.Intern;
import school.hei.employee.repository.InternRepository;

@Service
public class InternService {

  private final InternRepository repository;

  public InternService(InternRepository repository) {
    this.repository = repository;
  }

  public List<Intern> findAll(String department, Boolean remunere, Long managerId)
      throws SQLException {
    return repository.findAll(department, remunere, managerId);
  }

  public Intern findById(Long id) throws SQLException {
    return repository.findById(id);
  }

  public Intern save(Intern intern) throws SQLException {
    return repository.save(intern);
  }

  public Intern update(Long id, Intern intern) throws SQLException {
    return repository.update(id, intern);
  }

  public void delete(Long id) throws SQLException {
    repository.delete(id);
  }
}
