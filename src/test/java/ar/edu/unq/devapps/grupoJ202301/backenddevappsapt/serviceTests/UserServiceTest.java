package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.serviceTests;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service.UserService;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.factories.UserFactory;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model.User;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.anyUser();
    }
    @Test
    void register_Test() {
        Assertions.assertEquals(userService.register(user), "example@example.com");
    }
    @Test
    void register_user_with_incorrect_email_Test() {
        user.setEmail("example.com");
        Exception result = assertThrows(TransactionSystemException.class, () -> userService.register(user));
        assertTrue(result.getCause().getCause().getConstraintViolations()  .contentEquals("must be a well-formed email address"));
    }
}