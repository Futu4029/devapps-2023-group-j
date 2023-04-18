package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.UserException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.UserWebService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserWebServiceTest {
    @Autowired
    private UserWebService userWebService;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.anyUser();
    }

    private void genericStructureToRegisterTest(User user, HttpStatusCode statusCode, String body) {
        ResponseEntity<String> responseEntity = userWebService.register(user);
        Assertions.assertEquals(responseEntity.getStatusCode(), statusCode);
        Assertions.assertEquals(responseEntity.getBody(), body);
    }

    @Test
    void register_User_Test() {
        genericStructureToRegisterTest(user,HttpStatus.OK, "example@example.com");
    }

    @Test
    void register_User_With_Incorrect_Email_Test() {
        user.setEmail("example.com");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST, "Field email has an error: must be a well-formed email address");
    }

    @Test
    void register_User_With_Email_Already_Registerd_Test() {
        userWebService.register(user);
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field email has an error: Already in used");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Name_Test() {
        user.setName("ex");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field name has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_More_Than_Thirty_Characters_In_The_Name_Test() {
        user.setName("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field name has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_Incorrect_Characters_In_The_Name_Test() {
        user.setName("example!9");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field name has an error: Only letters are allowed");
    }

    @Test
    void register_User_With_Characters_Other_Than_Letters_Test() throws UserException {
        user.setName("1!_{L");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field name has an error: Only letters are allowed");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Surname_Test() {
        user.setSurname("ex");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field surname has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_More_Than_Thirty_Characters_In_The_Surname_Test() {
        user.setSurname("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field surname has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Address_Test() {
        user.setAddress("ex");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field address has an error: size must be between 10 and 30");
    }

    @Test
    void register_User_With_More_Than_Ten_Characters_In_The_Address_Test() {
        user.setAddress("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field address has an error: size must be between 10 and 30");
    }

    @Test
    void register_User_With_Incorrect_Address_Test() {
        user.setAddress("exampleC1-/!@");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field address has an error: Only letters and numbers are allowed");
    }

    @Test
    void register_User_With_Less_Than_Eight_Characters_In_The_WalletAddress_Test() {
        user.setWalletAddress("1A");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field walletAddress has an error: size must be between 8 and 8");
    }

    @Test
    void register_User_With_More_Than_Eight_Characters_In_The_WalletAddress_Test() {
        user.setWalletAddress("1A1A1A1A1A");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field walletAddress has an error: size must be between 8 and 8");
    }

    @Test
    void register_User_With_Incorrect_WalletAddress_Test() {
        user.setWalletAddress("1A!A1A@1");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field walletAddress has an error: Only letters and numbers are allowed");
    }

    @Test
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() {
        user.setCvu("1234567890");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field cvu has an error: size must be between 22 and 22");
    }

    @Test
    void register_User_With_More_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() {
        user.setCvu("12345678901234567890000");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field cvu has an error: size must be between 22 and 22");
    }

    @Test
    void register_User_With_Incorrect_CVU_Test() {
        user.setCvu("12345678901234!67890A0");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field cvu has an error: Only numbers are allowed");
    }

    @Test
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_Password_Test() {
        user.setPassword("ABc@1");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field password has an error: size must be between 6 and 32");
    }

    @Test
    void register_User_With_More_Than_ThirtyTwo_Characters_In_The_Password_Test() {
        user.setPassword("1234567890aB@!5678901234567890000");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field password has an error: size must be between 6 and 32");
    }

    @Test
    void register_User_With_Incorrect_Password_Test() {
        user.setPassword("EXAMPLE1aavb");
        genericStructureToRegisterTest(user,HttpStatus.BAD_REQUEST,"Field password has an error: Only letters, numbers and special characters are allowed");
    }
}
