package by.drozdov.employee.assembler;


import by.drozdov.employee.controller.EmployeeController;
import by.drozdov.employee.dto.EmployeeDto;
import by.drozdov.employee.entities.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeAssembler extends RepresentationModelAssemblerSupport<Employee, EmployeeDto> {

    public EmployeeAssembler() {
        super(EmployeeController.class, EmployeeDto.class);
    }

    @Override
    public CollectionModel<EmployeeDto> toCollectionModel(Iterable<? extends Employee> entities) {

        CollectionModel<EmployeeDto> userModels = super.toCollectionModel(entities);
        userModels.add(linkTo(methodOn(EmployeeController.class).findAllEmployee(null, null, null)).withSelfRel());

        return super.toCollectionModel(entities);
    }

    @Override
    public EmployeeDto toModel(Employee employee) {

        EmployeeDto employeeDto = instantiateModel(employee);

        employeeDto.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel());
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPassword(employee.getPassword());

        return employeeDto;
    }

}

