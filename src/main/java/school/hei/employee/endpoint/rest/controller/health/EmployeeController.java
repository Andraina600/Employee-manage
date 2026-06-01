package school.hei.employee.endpoint.rest.controller.health;

import java.sql.SQLException;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import school.hei.employee.entity.Employee;
import school.hei.employee.handler.NotFoundException;
import school.hei.employee.service.EmployeeService;
import school.hei.employee.validator.EmployeeValidator;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:5173/", exposedHeaders = "X-Total-Count")
public class EmployeeController {

  private final EmployeeService service;
  private final EmployeeValidator validator;

  public EmployeeController(EmployeeService service, EmployeeValidator validator) {
    this.service = service;
    this.validator = validator;
  }

  @GetMapping
  public ResponseEntity<?> getAll(
      @RequestParam(required = false) String department,
      @RequestParam(required = false) Boolean actif,
      @RequestParam(defaultValue = "0") int _start,
      @RequestParam(defaultValue = "10") int _end,
      @RequestParam(defaultValue = "id") String _sort,
      @RequestParam(defaultValue = "ASC") String _order) {
    try {
      List<Employee> result = service.findAll(department, actif, _start, _end, _sort, _order);
      int total = service.count(department, actif);
      HttpHeaders headers = new HttpHeaders();
      headers.add("X-Total-Count", String.valueOf(total));
      headers.add("Access-Control-Expose-Headers", "X-Total-Count");
      return ResponseEntity.status(HttpStatus.OK).headers(headers).body(result);
    } catch (SQLException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getOne(@PathVariable Long id) throws SQLException {
    Employee employee = service.findById(id);
    if (employee == null) throw new NotFoundException("Employee not found with id: " + id);
    return ResponseEntity.ok(employee);
  }

  @PostMapping
  public ResponseEntity<Employee> create(@RequestBody Employee employee) throws SQLException {
    validator.validate(employee);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(employee));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee)
      throws SQLException {
    Employee updatedEmployee = service.update(id, employee);
    if (updatedEmployee == null) throw new NotFoundException("Employee not found with id: " + id);
    return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body("Employee deleted");
  }
}
