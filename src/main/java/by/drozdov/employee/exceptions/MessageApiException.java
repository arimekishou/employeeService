package by.drozdov.employee.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class MessageApiException extends RuntimeException {

    private HttpStatus status;
    private String message;

}
