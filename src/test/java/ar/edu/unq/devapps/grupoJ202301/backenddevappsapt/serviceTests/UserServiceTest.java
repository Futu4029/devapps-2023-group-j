package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.serviceTests;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.validation.UserException;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.service.UserService;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.factories.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model.User;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    void register_User_With_Incorrect_Email_Test() {
        user.setEmail("example.com");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field email has an error: must be a well-formed email address", result.getMessage());
    }
    @Test
    void register_User_With_Email_Already_Registerd_Test() {
        userService.register(user);
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field email has an error: Already in used", result.getMessage());
    }
    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Name_Test() {
        user.setName("ex");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field name has an error: size must be between 3 and 30", result.getMessage());
    }
    @Test
    void register_User_With_More_Than_Thirty_Characters_In_The_Name_Test() {
        user.setName("exampleexampleexampleexampleexample");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field name has an error: size must be between 3 and 30", result.getMessage());
    }
    @Test
    void register_User_With_Incorrect_Characters_In_The_Name_Test() {
        user.setName("example!9");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field name has an error: Only letters are allowed", result.getMessage());
    }
    @Test
    void register_User_With_Characters_Other_Than_Letters_Test() throws UserException {
        user.setName("1!_{L");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field name has an error: Only letters are allowed", result.getMessage());
    }
    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Surname_Test() {
        user.setSurname("ex");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field surname has an error: size must be between 3 and 30", result.getMessage());
    }
    @Test
    void register_User_With_More_Than_Thirty_Characters_In_The_Surname_Test() {
        user.setSurname("exampleexampleexampleexampleexample");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field surname has an error: size must be between 3 and 30", result.getMessage());
    }
    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Address_Test() {
        user.setAddress("ex");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field address has an error: size must be between 10 and 30", result.getMessage());
    }
    @Test
    void register_User_With_More_Than_Ten_Characters_In_The_Address_Test() {
        user.setAddress("exampleexampleexampleexampleexample");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field address has an error: size must be between 10 and 30", result.getMessage());
    }
    @Test
    void register_User_With_Incorrect_Address_Test() {
        user.setAddress("C1-/!@");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field address has an error: Only letters and numbers are allowed", result.getMessage());
    }
    @Test
    void register_User_With_Less_Than_Eight_Characters_In_The_WalletAddress_Test() {
        user.setWalletAddress("1A");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field walletAddress has an error: size must be between 8 and 8", result.getMessage());
    }
    @Test
    void register_User_With_More_Than_Eight_Characters_In_The_WalletAddress_Test() {
        user.setWalletAddress("1A1A1A1A1A");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field walletAddress has an error: size must be between 8 and 8", result.getMessage());
    }
    @Test
    void register_User_With_Incorrect_WalletAddress_Test() {
        user.setWalletAddress("1A!A1A@1");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field walletAddress has an error: Only letters and numbers are allowed", result.getMessage());
    }
    @Test
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() {
        user.setCvu("1234567890");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field cvu has an error: size must be between 22 and 22", result.getMessage());
    }
    @Test
    void register_User_With_More_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() {
        user.setCvu("12345678901234567890000");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field cvu has an error: size must be between 22 and 22", result.getMessage());
    }
    @Test
    void register_User_With_Incorrect_CVU_Test() {
        user.setCvu("12345678901234!67890A0");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field cvu has an error: Only numbers are allowed", result.getMessage());
    }
    @Test
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_Password_Test() {
        user.setPassword("ABc@1");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field password has an error: size must be between 6 and 32", result.getMessage());
    }
    @Test
    void register_User_With_More_Than_ThirtyTwo_Characters_In_The_Password_Test() {
        user.setPassword("1234567890aB@!5678901234567890000");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field password has an error: size must be between 6 and 32", result.getMessage());
    }
    @Test
    void register_User_With_Incorrect_Password_Test() {
        user.setPassword("EXAMPLE1aavb");
        Exception result = assertThrows(UserException.class, () -> userService.register(user));
        Assertions.assertEquals("Field password has an error: Only letters, numbers and special characters are allowed", result.getMessage());
    }
}