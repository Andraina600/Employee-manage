package school.hei.employee.validator;

import java.sql.SQLException;
import org.springframework.stereotype.Component;
import school.hei.employee.entity.Employee;
import school.hei.employee.handler.BadRequestException;

@Component
public class EmployeeValidator {
  public void validate(Employee employee) throws SQLException {
    if (employee.getFirstname() == null || employee.getFirstname().isBlank())
      throw new BadRequestException("Firstname is required");
    if (employee.getEmail() == null || employee.getEmail().isBlank())
      throw new BadRequestException("Email is required");
  }
}
