package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.UserListDto;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoLogin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestClientException;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserWebServiceTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";
    private User user;
    private DtoLogin userLogin;
    private User userRoleUser;
    private User userRoleAdmin;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthHelper authHelper;

    @BeforeEach
    void setUp() {
        user = UserFactory.anyUser();
        userLogin = new DtoLogin(user.getEmail(), user.getPassword());
        userRoleUser = UserFactory.anyUserRoleUser();
        userRoleAdmin = UserFactory.anyUserRoleAdmin();
        authHelper.setData(HTTP_LOCALHOST, port, restTemplate);
    }

    private void genericStructureToRegisterTest(User user, int statusCode, String message) throws Exception {
        ResponseEntity<String> result = authHelper.registerUser(user);
        Assertions.assertEquals(message, result.getBody());
        Assertions.assertEquals(statusCode, result.getStatusCode().value());
    }

    private void genericStructureToLoginTest(DtoLogin userLogin, int statusCode, String message) throws Exception {
        authHelper.registerUser(user);
        ResponseEntity<String> result = authHelper.loginUser(userLogin);
        if(!message.isEmpty()) {Assertions.assertEquals(message, result.getBody());}
        Assertions.assertEquals(statusCode, result.getStatusCode().value());
    }

    @Test
    @DirtiesContext
    void register_User_With_Email_Already_Registerd_Test() throws Exception{
        genericStructureToRegisterTest(user, 400,  "ERROR: The User example@example.com is already registered.");
    }

    @Test
    @DirtiesContext
    void register_User_With_Empty_Email_Test() throws Exception {
        user.setEmail("");
        genericStructureToRegisterTest(user, 400,"Field email has an error: must not be blank");
    }

    @Test
    @DirtiesContext
    void register_User_With_Less_Than_Three_Characters_In_The_Name_Test() throws Exception {
        user.setName("ex");
        genericStructureToRegisterTest(user, 400, "Field name has an error: size must be between 3 and 30");
    }

    @Test
    @DirtiesContext
    void register_User_With_More_Than_Thirty_Characters_In_The_Name_Test() throws Exception {
        user.setName("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, 400, "Field name has an error: size must be between 3 and 30");
    }

    @Test
    @DirtiesContext
    void register_User_With_Incorrect_Characters_In_The_Name_Test() throws Exception {
        user.setName("example!9");
        genericStructureToRegisterTest(user, 400, "Field name has an error: Only letters are allowed");
    }

    @Test
    @DirtiesContext
    void register_User_With_Characters_Other_Than_Letters_Test() throws Exception {
        user.setName("1!_{L");
        genericStructureToRegisterTest(user, 400, "Field name has an error: Only letters are allowed");
    }

    @Test
    @DirtiesContext
    void register_User_With_Less_Than_Three_Characters_In_The_Surname_Test() throws Exception {
        user.setSurname("ex");
        genericStructureToRegisterTest(user, 400, "Field surname has an error: size must be between 3 and 30");
    }

    @Test
    @DirtiesContext
    void register_User_With_More_Than_Thirty_Characters_In_The_Surname_Test() throws Exception {
        user.setSurname("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, 400, "Field surname has an error: size must be between 3 and 30");
    }

    @Test
    @DirtiesContext
    void register_User_With_Less_Than_Three_Characters_In_The_Address_Test() throws Exception {
        user.setAddress("ex");
        genericStructureToRegisterTest(user, 400, "Field address has an error: size must be between 10 and 30");
    }

    @Test
    @DirtiesContext
    void register_User_With_More_Than_Ten_Characters_In_The_Address_Test() throws Exception {
        user.setAddress("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, 400, "Field address has an error: size must be between 10 and 30");
    }

    @Test
    @DirtiesContext
    void register_User_With_Incorrect_Address_Test() throws Exception {
        user.setAddress("exampleC1-/!@");
        genericStructureToRegisterTest(user, 400, "Field address has an error: Only letters and numbers are allowed");
    }

    @Test
    @DirtiesContext
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() throws Exception {
        user.setCvu("1234567890");
        genericStructureToRegisterTest(user, 400, "Field cvu has an error: size must be between 22 and 22");
    }

    @Test
    @DirtiesContext
    void register_User_With_More_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() throws Exception {
        user.setCvu("12345678901234567890000");
        genericStructureToRegisterTest(user, 400, "Field cvu has an error: size must be between 22 and 22");
    }

    @Test
    @DirtiesContext
    void register_User_With_Incorrect_CVU_Test() throws Exception {
        user.setCvu("12345678901234!67890A0");
        genericStructureToRegisterTest(user, 400, "Field cvu has an error: Only numbers are allowed");
    }

    @Test
    @DirtiesContext
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_Password_Test() throws Exception {
        user.setPassword("ABc@1");
        genericStructureToRegisterTest(user, 400, "Field password has an error: size must be between 6 and 32");
    }

    @Test
    @DirtiesContext
    void register_User_With_More_Than_ThirtyTwo_Characters_In_The_Password_Test() throws Exception {
        user.setPassword("1234567890aB@!5678901234567890000");
        genericStructureToRegisterTest(user, 400, "Field password has an error: size must be between 6 and 32");
    }

    @Test
    @DirtiesContext
    void register_User_With_Incorrect_Password_Test() throws Exception {
        user.setPassword("EXAMPLE1aavb");
        genericStructureToRegisterTest(user, 400, "Field password has an error: Only letters, numbers and special characters are allowed");
    }

    @Test
    @DirtiesContext
    void register_User_With_Less_Than_Eight_Characters_In_The_WalletAddress_Test() throws Exception {
        user = UserFactory.userWithLargeWalletAddress();
        genericStructureToRegisterTest(user, 400, "Field walletAddress has an error: size must be between 8 and 8");
    }

    @Test
    @DirtiesContext
    void register_User_With_More_Than_Eight_Characters_In_The_WalletAddress_Test() throws Exception {
        user = UserFactory.userWithShortWalletAddress();
        genericStructureToRegisterTest(user, 400, "Field walletAddress has an error: size must be between 8 and 8");
    }

    @Test
    @DirtiesContext
    void login_user_Test() throws Exception {
        genericStructureToLoginTest(userLogin, 200, ""); }

    @Test
    @DirtiesContext
    void login_User_With_Less_Than_TwentyTwo_Characters_In_The_Password_Test() throws Exception {
        userLogin.setPassword("Ab1!");
        genericStructureToLoginTest(userLogin, 400, "Field password has an error: size must be between 6 and 32");
    }

    @Test
    @DirtiesContext
    void login_User_With_More_Than_ThirtyTwo_Characters_In_The_Password_Test() throws Exception {
        userLogin.setPassword("1234567890aB@!5678901234567890000");
        genericStructureToLoginTest(userLogin, 400, "Field password has an error: size must be between 6 and 32");
    }

    @Test
    @DirtiesContext
    void login_User_With_Non_Valid_Password_Test() throws Exception {
        userLogin.setPassword("EXAMPLE1aavb");
        genericStructureToLoginTest(userLogin, 400, "Field password has an error: Only letters, numbers and special characters are allowed");
    }

    @Test
    @DirtiesContext
    void login_User_With_Incorrect_Password_Test() throws Exception {
        userLogin.setPassword("exampleP3!");
        genericStructureToLoginTest(userLogin, 400, "ERROR: The data entered is incorrect");
    }

    @Test
    @DirtiesContext
    void login_User_With_Incorrect_Email_Test() throws Exception {
        userLogin.setEmail("anotherEmail@example.com");
        genericStructureToLoginTest(userLogin, 400, "ERROR: The data entered is incorrect");
    }

    @Test
    @DirtiesContext
    void get_All_Users_With_USER_Role() throws Exception {
        genericStructureToRegisterTest(userRoleUser, 200, "user1@example.com");
        DtoLogin loginCred = new DtoLogin();
        loginCred.setEmail(userRoleUser.getEmail());
        loginCred.setPassword(userRoleUser.getPassword());
        String urlProceedOne = HTTP_LOCALHOST + port + "/auth";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(loginCred));
        ParameterizedTypeReference<List<UserListDto>> responseType = new ParameterizedTypeReference<List<UserListDto>>() {};
        Assertions.assertThrows(RestClientException.class, () -> restTemplate.exchange(urlProceedOne, HttpMethod.GET, new HttpEntity<>(headers), responseType));
    }
    @Test
    @DirtiesContext
    void get_All_Users_With_ADMIN_Role() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.CONTENT_TYPE, Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userRoleAdmin);
        var http = new HttpEntity(json, headers);
        restTemplate.exchange(HTTP_LOCALHOST + port + "/auth/registerAdmin", HttpMethod.POST, http, String.class);
        DtoLogin loginCred = new DtoLogin();
        loginCred.setEmail(userRoleAdmin.getEmail());
        loginCred.setPassword(userRoleAdmin.getPassword());
        String urlProceedOne = HTTP_LOCALHOST + port + "/auth";
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.APPLICATION_JSON);
        headers1.setBearerAuth(authHelper.getToken(loginCred));
        ParameterizedTypeReference<List<UserListDto>> responseType = new ParameterizedTypeReference<List<UserListDto>>() {};
        ResponseEntity<List<UserListDto>> response = restTemplate.exchange(urlProceedOne, HttpMethod.GET, new HttpEntity<>(headers1), responseType);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertFalse(response.getBody().isEmpty());
        Assertions.assertEquals(3, response.getBody().size());
    }
}