package by.drozdov.employee.repository;

import by.drozdov.employee.entities.Employee;
import by.drozdov.employee.entities.EmployeeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface EmployeeCategoryRepository extends JpaRepository<EmployeeCategory, Long>, JpaSpecificationExecutor<EmployeeCategory> {

    EmployeeCategory findByName(String name);

    EmployeeCategory getById(Long id);

    Page<EmployeeCategory> findAll(Pageable pageable);

    List<EmployeeCategory> getAllByEmployee(Employee employee);

}
