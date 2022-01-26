package by.drozdov.employee.converter;

import by.drozdov.employee.dto.EmployeeCategoryDto;
import by.drozdov.employee.entities.EmployeeCategory;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCategoryConverter {

    public EmployeeCategory fromEmployeeCategoryDtoToEmployeeCategory(EmployeeCategoryDto employeeCategoryDto) {

        EmployeeCategory employeeCategory = new EmployeeCategory();
        employeeCategory.setName(employeeCategoryDto.getName());
        employeeCategory.setEmployee(employeeCategoryDto.getEmployee());

        return employeeCategory;
    }

    public EmployeeCategoryDto fromEmployeeCategoryToEmployeeCategoryDto(EmployeeCategory employeeCategory) {

        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setId(employeeCategory.getId());
        employeeCategoryDto.setEmployee(employeeCategory.getEmployee());
        employeeCategoryDto.setName(employeeCategory.getName());

        return employeeCategoryDto;
    }

}
