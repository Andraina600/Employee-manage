package school.hei.employee.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Intern {
  private Long id;
  private String firstname;
  private String lastname;
  private String email;
  private String department;
  private Double salary;
  private Boolean remunere;
  private Boolean actif;
  private Long managerId;
}
