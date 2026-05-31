package school.hei.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String department;
    private Double salary;
    private Boolean actif;
}
