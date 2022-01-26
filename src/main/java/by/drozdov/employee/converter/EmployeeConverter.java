package by.drozdov.employee.converter;

import by.drozdov.employee.dto.EmployeeDto;
import by.drozdov.employee.entities.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    public Employee fromUserDtoToUser(EmployeeDto userDto) {

        Employee user = new Employee();
        user.setFirstName(user.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(user.getPassword());

        return user;
    }

    public EmployeeDto fromUserToUserDto(Employee user) {

        EmployeeDto userDto = new EmployeeDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

}