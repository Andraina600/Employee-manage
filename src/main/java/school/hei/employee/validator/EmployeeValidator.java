package school.hei.employee.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import school.hei.employee.entity.Employee;
import school.hei.employee.handler.BadRequestException;

import java.sql.SQLException;

@Component
public class EmployeeValidator {
    public void validate(Employee employee) throws SQLException {
        if (employee.getFirstname() == null || employee.getFirstname().isBlank())
            throw new BadRequestException("Firstname is required");
        if (employee.getEmail() == null || employee.getEmail().isBlank())
            throw new BadRequestException("Email is required");
    }
}
