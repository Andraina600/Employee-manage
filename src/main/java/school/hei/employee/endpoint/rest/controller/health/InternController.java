package school.hei.employee.endpoint.rest.controller.health;

import java.sql.SQLException;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import school.hei.employee.entity.Intern;
import school.hei.employee.handler.NotFoundException;
import school.hei.employee.service.InternService;
import school.hei.employee.validator.InternValidator;

@RestController
@RequestMapping("/interns")
@CrossOrigin(origins = "http://localhost:5173/", exposedHeaders = "X-Total-Count")
public class InternController {

  private final InternService service;
  private final InternValidator validator;

  public InternController(InternService service, InternValidator validator) {
    this.service = service;
    this.validator = validator;
  }

  @GetMapping
  public ResponseEntity<?> getAll(
      @RequestParam(required = false) String department,
      @RequestParam(required = false) Boolean remunere,
      @RequestParam(required = false) Long managerId,
      @RequestParam(defaultValue = "0") int _start,
      @RequestParam(defaultValue = "10") int _end,
      @RequestParam(defaultValue = "id") String _sort,
      @RequestParam(defaultValue = "ASC") String _order) {
    try {
      List<Intern> result =
          service.findAll(department, remunere, managerId, _start, _end, _sort, _order);
      int total = service.count(department, remunere, managerId);
      HttpHeaders headers = new HttpHeaders();
      headers.add("X-Total-Count", String.valueOf(total));
      headers.add("Access-Control-Expose-Headers", "X-Total-Count");
      return ResponseEntity.ok().headers(headers).body(result);
    } catch (SQLException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Intern> getOne(@PathVariable Long id) throws SQLException {
    Intern intern = service.findById(id);
    if (intern == null) throw new NotFoundException("Intern not found with id: " + id);
    return ResponseEntity.ok(intern);
  }

  @PostMapping
  public ResponseEntity<Intern> create(@RequestBody Intern intern) throws SQLException {
    validator.validate(intern);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(intern));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Intern> update(@PathVariable Long id, @RequestBody Intern intern)
      throws SQLException {
    Intern updatedIntern = service.update(id, intern);
    return ResponseEntity.status(HttpStatus.OK).body(updatedIntern);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body("Intern deleted");
  }
}
