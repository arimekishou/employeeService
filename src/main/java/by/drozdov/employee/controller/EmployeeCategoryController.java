package by.drozdov.employee.controller;

import by.drozdov.employee.converter.EmployeeCategoryConverter;
import by.drozdov.employee.dto.EmployeeCategoryDto;
import by.drozdov.employee.entities.Employee;
import by.drozdov.employee.entities.EmployeeCategory;
import by.drozdov.employee.repository.EmployeeCategoryRepository;
import by.drozdov.employee.service.impl.EmployeeCategoryServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/employeeCategory/")
@AllArgsConstructor
@Log
public class EmployeeCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeCategoryController.class);
    private final EmployeeCategoryServiceImpl employeeCategoryService;
    private final EmployeeCategoryRepository employeeCategoryRepository;
    private final EmployeeCategoryConverter employeeCategoryConverter;

    @PostMapping(value = "/add")
    public ResponseEntity<?> createCategory(@Valid @AuthenticationPrincipal Employee employee, String name) {

        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setName(name);
        employeeCategoryDto.setEmployee(employee);
        employeeCategoryService.saveEmployeeCategory(employeeCategoryDto);
        LOGGER.info("Category created");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    public CollectionModel<EmployeeCategoryDto> findAll(@RequestParam Integer page,
                                                        @RequestParam Integer size,
                                                        @RequestParam String sort) {

        LOGGER.info("Handing find all category request");

        return employeeCategoryService.findAll(page, size, sort);
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<EntityModel<EmployeeCategoryDto>> getCategory(@PathVariable(name = "id") Long id) {

        LOGGER.info("Handling find category request" + employeeCategoryService.getById(id).toString());
        EmployeeCategoryDto employeeCategoryDto = employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategoryService.getById(id));
        System.err.println(employeeCategoryDto.toString());
        LOGGER.info("User getting by ID");

        return new ResponseEntity<>(EntityModel.of(employeeCategoryDto,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(id)).withSelfRel()), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                    @Valid @AuthenticationPrincipal Employee employee, EmployeeCategoryDto employeeCategoryDto,
                                    EmployeeCategory employeeCategory) {

        if (employeeCategoryRepository.existsById(id)) {
            LOGGER.info("Handling update category request" + employeeCategoryDto);

            if (employeeCategoryDto.getName() != null) {
                employeeCategory.setEmployee(employee);
                employeeCategory.setName(employeeCategoryDto.getName());
            }

            employeeCategoryRepository.save(employeeCategory);

            return ResponseEntity.ok().build();
        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable(name = "id") Long id) {

        if (employeeCategoryRepository.existsById(id)) {
            employeeCategoryService.deleteById(id);
            LOGGER.info("Task group deleted");
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
