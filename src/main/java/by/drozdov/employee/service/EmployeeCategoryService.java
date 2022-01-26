package by.drozdov.employee.service;

import by.drozdov.employee.dto.EmployeeCategoryDto;
import by.drozdov.employee.entities.Employee;
import by.drozdov.employee.entities.EmployeeCategory;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

public interface EmployeeCategoryService {

    EmployeeCategoryDto saveEmployeeCategory(EmployeeCategoryDto employeeCategoryDto);

    EmployeeCategory getById(Long id);

    EmployeeCategoryDto findByName(String name);

    CollectionModel<EmployeeCategoryDto> findAll(Integer page, Integer size, String sort);

    List<EmployeeCategory> getAllByEmployee(Employee employee);

    void deleteById(Long id);

    void updateCategory(EmployeeCategoryDto employeeCategoryDto);

}
