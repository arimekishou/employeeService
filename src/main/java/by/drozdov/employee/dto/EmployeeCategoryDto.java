package by.drozdov.employee.dto;

import by.drozdov.employee.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeCategoryDto extends RepresentationModel<EmployeeCategoryDto> {

    @Null
    private Long id;
    @Null
    private Employee employee;
    @NotNull
    private String name;

}
