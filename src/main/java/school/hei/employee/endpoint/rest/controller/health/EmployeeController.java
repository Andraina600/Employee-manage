package school.hei.employee.endpoint.rest.controller.health;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import school.hei.employee.entity.Employee;
import school.hei.employee.service.EmployeeService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*", exposedHeaders = "Content-Range")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Boolean actif
    ) throws SQLException {
        List<Employee> result = service.findAll(department, actif);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", "employees 0-" + result.size() + "/" + result.size());
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable Long id) throws SQLException {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        return service.save(employee);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee employee) throws SQLException {
        return service.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws SQLException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}