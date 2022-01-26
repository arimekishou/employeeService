package by.drozdov.employee.service.impl;

import by.drozdov.employee.assembler.EmployeeCategoryAssembler;
import by.drozdov.employee.converter.EmployeeCategoryConverter;
import by.drozdov.employee.dto.EmployeeCategoryDto;
import by.drozdov.employee.entities.Employee;
import by.drozdov.employee.entities.EmployeeCategory;
import by.drozdov.employee.repository.EmployeeCategoryRepository;
import by.drozdov.employee.service.EmployeeCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeCategoryServiceImpl implements EmployeeCategoryService {


    private final EmployeeCategoryConverter employeeCategoryConverter;
    private final EmployeeCategoryRepository employeeCategoryRepository;
    private EmployeeCategoryAssembler assembler;

    @Override
    public EmployeeCategoryDto saveEmployeeCategory(EmployeeCategoryDto employeeCategoryDto) {

        EmployeeCategory employeeCategory = employeeCategoryRepository.save(employeeCategoryConverter.fromEmployeeCategoryDtoToEmployeeCategory(employeeCategoryDto));

        return employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory);
    }

    @Override
    public EmployeeCategory getById(Long id) {

        if (employeeCategoryRepository.getById(id) != null) {
            return employeeCategoryRepository.getById(id);
        }
        return null;
    }

    @Override
    public CollectionModel<EmployeeCategoryDto> findAll(Integer page, Integer size, String sort) {

        int pages = page != null ? page : 0;
        int sizes = size != null ? size : 5;
        String sorts = sort != null && !sort.equals("") ? sort : "name";
        Pageable pageable = PageRequest.of(pages, sizes, Sort.by(sorts));
        Page<EmployeeCategory> employeeCategories = employeeCategoryRepository.findAll(pageable);

        return !employeeCategories.isEmpty() ? assembler.toCollectionModel(employeeCategories) : null;
    }

    @Override
    public List<EmployeeCategory> getAllByEmployee(Employee employee) {
        return employeeCategoryRepository.getAllByEmployee(employee);
    }

    @Override
    public void updateCategory(EmployeeCategoryDto employeeCategoryDto) {
        employeeCategoryRepository.save(employeeCategoryConverter.fromEmployeeCategoryDtoToEmployeeCategory(employeeCategoryDto));
    }

    @Override
    public EmployeeCategoryDto findByName(String name) {

        EmployeeCategory employeeCategory = employeeCategoryRepository.findByName(name);

        if (employeeCategory != null) {
            return employeeCategoryConverter.fromEmployeeCategoryToEmployeeCategoryDto(employeeCategory);
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {
        employeeCategoryRepository.deleteById(id);
    }

}
