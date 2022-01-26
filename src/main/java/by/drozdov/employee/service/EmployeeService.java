package by.drozdov.employee.service;

import by.drozdov.employee.dto.EmployeeDto;
import by.drozdov.employee.entities.Employee;
import org.springframework.hateoas.CollectionModel;

public interface EmployeeService {

    CollectionModel<EmployeeDto> findAll(Integer page, Integer size, String sort);

    Employee getById(Long id);

    void deleteById(Long id);

}
