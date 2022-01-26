package by.drozdov.employee.service.impl;

import by.drozdov.employee.assembler.EmployeeAssembler;
import by.drozdov.employee.dto.EmployeeDto;
import by.drozdov.employee.entities.Employee;
import by.drozdov.employee.repository.EmployeeCategoryRepository;
import by.drozdov.employee.repository.EmployeeRepository;
import by.drozdov.employee.security.registration.token.ConfirmationToken;
import by.drozdov.employee.security.registration.token.ConfirmationTokenService;
import by.drozdov.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements UserDetailsService, EmployeeService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final EmployeeRepository employeeRepository;
    private final EmployeeCategoryRepository employeeCategoryRepository;
    private final EmployeeCategoryServiceImpl employeeCategoryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private EmployeeAssembler assembler;

    @Autowired
    private CacheManager cacheManager;

    public void addToCache(Employee emp) {
        Cache cache = cacheManager.getCache("employeeCache");
        cache.putIfAbsent(emp.getId(), emp);
    }

    @Override
    public CollectionModel<EmployeeDto> findAll(Integer page, Integer size, String sort) {

        int pages = page != null ? page : 0;
        int sizes = size != null ? size : 5;
        String sorts = sort != null && !sort.equals("") ? sort : "name";
        Pageable pageable = PageRequest.of(pages, sizes, Sort.by(sorts));
        Page<Employee> users = employeeRepository.findAll(pageable);

        for (Employee emp : users) {
            addToCache(emp);
        }
        return !users.isEmpty() ? assembler.toCollectionModel(users) : null;
    }

    @Override
    public Employee getById(Long id) {
        if (employeeRepository.getById(id) != null) {
            return employeeRepository.getById(id);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        if (employeeCategoryRepository.existsById(id)) {
            employeeCategoryService.deleteById(id);
            confirmationTokenService.deleteConfirmationTokenByAppUserId(id);
            employeeRepository.deleteById(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return employeeRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(Employee employee) {

        boolean userExists = employeeRepository.findByEmail(employee.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());

        employee.setPassword(encodedPassword);

        employeeRepository.save(employee);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                employee
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return employeeRepository.enableAppUser(email);
    }

}
