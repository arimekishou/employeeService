package by.drozdov.employee.security.registration;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;

    @PostMapping
    public String register(RegistrationRequest request) {
        LOGGER.info("User is registration");
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        LOGGER.info("Token confirmed");
        return registrationService.confirmToken(token);
    }

}
