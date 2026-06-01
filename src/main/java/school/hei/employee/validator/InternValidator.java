package school.hei.employee.validator;

import org.springframework.stereotype.Component;
import school.hei.employee.entity.Intern;
import school.hei.employee.handler.BadRequestException;

@Component
public class InternValidator {
  public void validate(Intern intern) {
    if (intern.getFirstname() == null || intern.getFirstname().isBlank())
      throw new BadRequestException("Firstname is required");
    if (intern.getManagerId() == null) throw new BadRequestException("Manager is required");
  }
}
