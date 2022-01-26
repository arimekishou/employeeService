package by.drozdov.employee.security.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String password;

}
