package by.vironit.taskscheduler.service.impl;

import by.drozdov.employee.assembler.EmployeeCategoryAssembler;
import by.drozdov.employee.converter.EmployeeCategoryConverter;
import by.drozdov.employee.dto.EmployeeCategoryDto;
import by.drozdov.employee.entities.Employee;
import by.drozdov.employee.entities.EmployeeCategory;
import by.drozdov.employee.repository.EmployeeCategoryRepository;
import by.drozdov.employee.service.impl.EmployeeCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ContextConfiguration(classes = {EmployeeCategoryServiceImpl.class,
        EmployeeCategoryRepository.class,
        EmployeeCategoryConverter.class,
        EmployeeCategoryAssembler.class})
class EmployeeCategoryServiceImplTest {


    @Autowired
    @InjectMocks
    private EmployeeCategoryServiceImpl taskGroupsService;

    @Autowired
    @Mock
    private EmployeeCategoryConverter employeeCategoryConverter;

    @Mock
    private EmployeeCategoryRepository employeeCategoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveTaskGroup() {

        long id = 1;
        String name = "hello";
        Employee employee = new Employee();
        EmployeeCategory employeeCategory = new EmployeeCategory();
        employeeCategory.setId(id);
        employeeCategory.setName(name);
        employeeCategory.setEmployee(employee);
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setId(id);
        employeeCategoryDto.setName(name);
        employeeCategoryDto.setEmployee(employee);
        Mockito.when(employeeCategoryRepository.save(employeeCategory)).thenReturn(employeeCategory);
        Mockito.when(employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory)).thenReturn(employeeCategoryDto);
    }

    @Test
    void getById() {

        long id = 1;
        EmployeeCategory employeeCategory = new EmployeeCategory();
        employeeCategory.setId(id);
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setId(id);
        Mockito.when(employeeCategoryRepository.getById(id)).thenReturn(employeeCategory);
        Mockito.when(employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory)).thenReturn(employeeCategoryDto);
        EmployeeCategory expected = taskGroupsService.getById(id);
        EmployeeCategoryDto actual = new EmployeeCategoryDto();
        actual.setId(id);
        assertEquals(expected, actual);
    }

    @Test
    void findAll() {

        EmployeeCategory employeeCategory = new EmployeeCategory();
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        Mockito.when(employeeCategoryRepository.findAll()).thenReturn(Collections.singletonList(employeeCategory));
        Mockito.when(employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory)).thenReturn(employeeCategoryDto);
    }

    @Test
    void getAllByAppUser() {

        Employee employee = new Employee();
        EmployeeCategory employeeCategory = new EmployeeCategory();
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        Mockito.when(employeeCategoryRepository.getAllByEmployee(employee)).thenReturn(Collections.singletonList(employeeCategory));
        Mockito.when(employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory)).thenReturn(employeeCategoryDto);
    }

    @Test
    void updateTaskGroup() {

        EmployeeCategory employeeCategory = new EmployeeCategory();
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        Mockito.when(employeeCategoryRepository.findAll()).thenReturn(Collections.singletonList(employeeCategory));
        employeeCategory.setName("hi");
        Mockito.when(employeeCategoryRepository.save(employeeCategory)).thenReturn(employeeCategory);
        Mockito.when(employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory)).thenReturn(employeeCategoryDto);
    }

    @Test
    void findByTitlePositive() {

        String name = "hello";
        EmployeeCategory employeeCategory = new EmployeeCategory();
        employeeCategory.setName(name);
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setName(name);
        Mockito.when(employeeCategoryRepository.findByName(name)).thenReturn(employeeCategory);
        Mockito.when(employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory)).thenReturn(employeeCategoryDto);
        EmployeeCategoryDto expected = taskGroupsService.findByName(name);
        EmployeeCategoryDto actual = new EmployeeCategoryDto();
        actual.setName(name);
        assertEquals(expected, actual);
    }

    @Test
    public void findByTitleNegative() {

        String name = "hello";
        EmployeeCategory employeeCategory = new EmployeeCategory();
        employeeCategory.setName(name);
        EmployeeCategoryDto employeeCategoryDto = new EmployeeCategoryDto();
        employeeCategoryDto.setName(name);
        Mockito.when(employeeCategoryRepository.findByName(name)).thenReturn(employeeCategory);
        Mockito.when(employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory)).thenReturn(employeeCategoryDto);
        EmployeeCategoryDto expected = taskGroupsService.findByName(name);
        EmployeeCategoryDto actual = new EmployeeCategoryDto();
        actual.setName("hi");
        assertNotEquals(expected, actual);
    }


    @Test
    void deleteById() {

        long id = 1;
        EmployeeCategory employeeCategory = new EmployeeCategory();
        employeeCategory.setId(id);
        taskGroupsService.deleteById(id);
    }

}