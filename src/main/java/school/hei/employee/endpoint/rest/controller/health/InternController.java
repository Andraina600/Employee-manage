package school.hei.employee.endpoint.rest.controller.health;

import java.sql.SQLException;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import school.hei.employee.entity.Intern;
import school.hei.employee.service.InternService;

@RestController
@RequestMapping("/interns")
@CrossOrigin(origins = "*", exposedHeaders = "Content-Range")
public class InternController {

  private final InternService service;

  public InternController(InternService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Intern>> getAll(
      @RequestParam(required = false) String department,
      @RequestParam(required = false) Boolean remunere,
      @RequestParam(required = false) Long managerId)
      throws SQLException {
    List<Intern> result = service.findAll(department, remunere, managerId);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Range", "interns 0-" + result.size() + "/" + result.size());
    return ResponseEntity.ok().headers(headers).body(result);
  }

  @GetMapping("/{id}")
  public Intern getOne(@PathVariable Long id) throws SQLException {
    return service.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Intern create(@RequestBody Intern intern) throws SQLException {
    return service.save(intern);
  }

  @PutMapping("/{id}")
  public Intern update(@PathVariable Long id, @RequestBody Intern intern) throws SQLException {
    return service.update(id, intern);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) throws SQLException {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
