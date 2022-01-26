package by.drozdov.employee.controller;

import by.drozdov.employee.converter.EmployeeConverter;
import by.drozdov.employee.dto.EmployeeDto;
import by.drozdov.employee.entities.Employee;
import by.drozdov.employee.repository.EmployeeRepository;
import by.drozdov.employee.service.impl.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log
@RestController
@RequestMapping(value = "/employee/")
@AllArgsConstructor
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeServiceImpl employeeService;
    private final EmployeeConverter employeeConverter;
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/find/all")
    public CollectionModel<EmployeeDto> findAllEmployee(@RequestParam Integer page,
                                                        @RequestParam Integer size,
                                                        @RequestParam String sort) {

        LOGGER.info("Handing find all employee request");

        return employeeService.findAll(page, size, sort);
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<EntityModel<EmployeeDto>> getEmployeeById(@PathVariable(name = "id") Long id) {

        LOGGER.info("Handling find account request" + employeeService.getById(id).toString());
        EmployeeDto userDto = employeeConverter.fromUserToUserDto(employeeService.getById(id));
        System.err.println(userDto.toString());
        LOGGER.info("Employee getting by ID");

        return new ResponseEntity<>(EntityModel.of(userDto,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(id)).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/findByEmail")
    public UserDetails findByEmail(@RequestParam String email) {
        LOGGER.info("Handling find by email request: " + email);
        return employeeService.loadUserByUsername(email);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable(name = "id") Long id,
                                               @Valid @AuthenticationPrincipal Employee user, EmployeeDto userDto) {
        if (employeeRepository.existsById(id)) {
            LOGGER.info("Handling update user request" + user);
            if (userDto.getFirstName() != null) {
                user.setFirstName(userDto.getFirstName());
            }
            if (userDto.getLastName() != null) {
                user.setLastName(userDto.getLastName());
            }
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }
            if (userDto.getPassword() != null) {
                user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            }

            employeeRepository.save(user);

            return ResponseEntity.ok().build();
        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {

        if (employeeRepository.existsById(id)) {
            employeeService.deleteById(id);
            LOGGER.info("Employee deleted");

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
