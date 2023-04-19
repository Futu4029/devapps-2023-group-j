package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.serviceTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.UserException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceTest {
    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.anyUser();
    }

    private void genericStructureToRegisterTest(User user, String messageError) {
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals(messageError, result.getMessage());
    }

    @Test
    void register_User_Test() {
        Assertions.assertEquals("example@example.com", userService.register(user));
    }

    @Test
    void register_User_With_Incorrect_Email_Test() {
        user.setEmail("example.com");
        genericStructureToRegisterTest(user, "Field email has an error: must be a well-formed email address");
    }

    @Test
    void register_User_With_Email_Already_Registerd_Test() {
        userService.register(user);
        genericStructureToRegisterTest(user, "Field email has an error: Already in used");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Name_Test() {
        user.setName("ex");
        genericStructureToRegisterTest(user, "Field name has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_More_Than_Thirty_Characters_In_The_Name_Test() {
        user.setName("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, "Field name has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_Incorrect_Characters_In_The_Name_Test() {
        user.setName("example!9");
        genericStructureToRegisterTest(user, "Field name has an error: Only letters are allowed");
    }

    @Test
    void register_User_With_Characters_Other_Than_Letters_Test() throws UserException {
        user.setName("1!_{L");
        genericStructureToRegisterTest(user, "Field name has an error: Only letters are allowed");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Surname_Test() {
        user.setSurname("ex");
        genericStructureToRegisterTest(user, "Field surname has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_More_Than_Thirty_Characters_In_The_Surname_Test() {
        user.setSurname("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, "Field surname has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Address_Test() {
        user.setAddress("ex");
        genericStructureToRegisterTest(user, "Field address has an error: size must be between 10 and 30");
    }

    @Test
    void register_User_With_More_Than_Ten_Characters_In_The_Address_Test() {
        user.setAddress("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, "Field address has an error: size must be between 10 and 30");
    }

    @Test
    void register_User_With_Incorrect_Address_Test() {
        user.setAddress("exampleC1-/!@");
        genericStructureToRegisterTest(user, "Field address has an error: Only letters and numbers are allowed");
    }

    @Test
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() {
        user.setCvu("1234567890");
        genericStructureToRegisterTest(user, "Field cvu has an error: size must be between 22 and 22");
    }

    @Test
    void register_User_With_More_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() {
        user.setCvu("12345678901234567890000");
        genericStructureToRegisterTest(user, "Field cvu has an error: size must be between 22 and 22");
    }

    @Test
    void register_User_With_Incorrect_CVU_Test() {
        user.setCvu("12345678901234!67890A0");
        genericStructureToRegisterTest(user, "Field cvu has an error: Only numbers are allowed");
    }

    @Test
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_Password_Test() {
        user.setPassword("ABc@1");
        genericStructureToRegisterTest(user, "Field password has an error: size must be between 6 and 32");
    }

    @Test
    void register_User_With_More_Than_ThirtyTwo_Characters_In_The_Password_Test() {
        user.setPassword("1234567890aB@!5678901234567890000");
        genericStructureToRegisterTest(user, "Field password has an error: size must be between 6 and 32");
    }

    @Test
    void register_User_With_Incorrect_Password_Test() {
        user.setPassword("EXAMPLE1aavb");
        genericStructureToRegisterTest(user, "Field password has an error: Only letters, numbers and special characters are allowed");
    }
}
