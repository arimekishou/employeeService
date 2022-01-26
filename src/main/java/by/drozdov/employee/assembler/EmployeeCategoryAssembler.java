package by.drozdov.employee.assembler;

import by.drozdov.employee.dto.EmployeeCategoryDto;
import by.drozdov.employee.entities.EmployeeCategory;
import by.drozdov.employee.controller.EmployeeCategoryController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeCategoryAssembler extends RepresentationModelAssemblerSupport<EmployeeCategory, EmployeeCategoryDto> {

    public EmployeeCategoryAssembler() {
        super(EmployeeCategoryController.class, EmployeeCategoryDto.class);
    }

    @Override
    public CollectionModel<EmployeeCategoryDto> toCollectionModel(Iterable<? extends EmployeeCategory> entities) {

        CollectionModel<EmployeeCategoryDto> taskGroupsDtoCollectionModel = super.toCollectionModel(entities);
        taskGroupsDtoCollectionModel.add(linkTo(methodOn(EmployeeCategoryController.class)
                .findAll(null, null, null)).withSelfRel());

        return super.toCollectionModel(entities);
    }

    @Override
    public EmployeeCategoryDto toModel(EmployeeCategory employeeCategory) {

        EmployeeCategoryDto employeeCategoryDto = instantiateModel(employeeCategory);

        employeeCategoryDto.add(linkTo(methodOn(EmployeeCategoryController.class).getCategory(employeeCategory.getId())).withSelfRel());
        employeeCategoryDto.setId(employeeCategory.getId());
        employeeCategoryDto.setEmployee(employeeCategory.getEmployee());
        employeeCategoryDto.setName(employeeCategory.getName());

        return employeeCategoryDto;
    }

}
