package by.vironit.taskscheduler.repository;

import by.drozdov.employee.assembler.EmployeeCategoryAssembler;
import by.drozdov.employee.converter.EmployeeCategoryConverter;
import by.drozdov.employee.entities.Employee;
import by.drozdov.employee.repository.EmployeeCategoryRepository;
import by.drozdov.employee.repository.EmployeeRepository;
import by.drozdov.employee.service.impl.EmployeeCategoryServiceImpl;
import by.drozdov.employee.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@ContextConfiguration(classes = {EmployeeServiceImpl.class,
        EmployeeRepository.class})
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee createdUser;

    @BeforeEach
    public void setUp() {
        Employee user = new Employee();
        createdUser = employeeRepository.save(user);
    }

    @Test
    void findByEmail() {

        String email = createdUser.getEmail();
        Optional<Employee> foundUser = employeeRepository.findByEmail(email);
        then(foundUser.orElse(null)).isEqualTo(createdUser);
    }

    @Test
    void getById() {

        Long id = createdUser.getId();
        Optional<Employee> foundUser = Optional.ofNullable(employeeRepository.getById(id));
        then(foundUser.orElse(null)).isEqualTo(createdUser);
    }

    @AfterEach
    public void deleteObject() {
        employeeRepository.delete(createdUser);
    }

}