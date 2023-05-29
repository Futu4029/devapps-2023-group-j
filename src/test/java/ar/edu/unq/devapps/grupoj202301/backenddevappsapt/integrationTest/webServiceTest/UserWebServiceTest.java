package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.integrationTest.webServiceTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.anyUser();
    }

    @AfterEach
    void cleanUp() {
        mockMvc = null;
    }

    private void genericStructureToRegisterTest(User user, int statusCode, String message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String userToRegister = objectMapper.writeValueAsString(user);
        var result = mockMvc.perform(post("/user/register")
                        .content(userToRegister)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(statusCode))
                        .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), message);
    }

    @Test
    void register_User_With_Email_Already_Registerd_Test() throws Exception{
        genericStructureToRegisterTest(user, 200,  "example@example.com");
        genericStructureToRegisterTest(user, 400,  "Field email has an error: Already in used");
    }

    @Test
    void register_User_With_Empty_Email_Test() throws Exception {
        user.setEmail("");
        genericStructureToRegisterTest(user, 400,"Field email has an error: must not be blank");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Name_Test() throws Exception {
        user.setName("ex");
        genericStructureToRegisterTest(user, 400, "Field name has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_More_Than_Thirty_Characters_In_The_Name_Test() throws Exception {
        user.setName("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, 400, "Field name has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_Incorrect_Characters_In_The_Name_Test() throws Exception {
        user.setName("example!9");
        genericStructureToRegisterTest(user, 400, "Field name has an error: Only letters are allowed");
    }

    @Test
    void register_User_With_Characters_Other_Than_Letters_Test() throws Exception {
        user.setName("1!_{L");
        genericStructureToRegisterTest(user, 400, "Field name has an error: Only letters are allowed");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Surname_Test() throws Exception {
        user.setSurname("ex");
        genericStructureToRegisterTest(user, 400, "Field surname has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_More_Than_Thirty_Characters_In_The_Surname_Test() throws Exception {
        user.setSurname("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, 400, "Field surname has an error: size must be between 3 and 30");
    }

    @Test
    void register_User_With_Less_Than_Three_Characters_In_The_Address_Test() throws Exception {
        user.setAddress("ex");
        genericStructureToRegisterTest(user, 400, "Field address has an error: size must be between 10 and 30");
    }

    @Test
    void register_User_With_More_Than_Ten_Characters_In_The_Address_Test() throws Exception {
        user.setAddress("exampleexampleexampleexampleexample");
        genericStructureToRegisterTest(user, 400, "Field address has an error: size must be between 10 and 30");
    }

    @Test
    void register_User_With_Incorrect_Address_Test() throws Exception {
        user.setAddress("exampleC1-/!@");
        genericStructureToRegisterTest(user, 400, "Field address has an error: Only letters and numbers are allowed");
    }

    @Test
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() throws Exception {
        user.setCvu("1234567890");
        genericStructureToRegisterTest(user, 400, "Field cvu has an error: size must be between 22 and 22");
    }

    @Test
    void register_User_With_More_Than_TwentyTwo_Characters_In_The_WalletAddress_Test() throws Exception {
        user.setCvu("12345678901234567890000");
        genericStructureToRegisterTest(user, 400, "Field cvu has an error: size must be between 22 and 22");
    }

    @Test
    void register_User_With_Incorrect_CVU_Test() throws Exception {
        user.setCvu("12345678901234!67890A0");
        genericStructureToRegisterTest(user, 400, "Field cvu has an error: Only numbers are allowed");
    }

    @Test
    void register_User_With_Less_Than_TwentyTwo_Characters_In_The_Password_Test() throws Exception {
        user.setPassword("ABc@1");
        genericStructureToRegisterTest(user, 400, "Field password has an error: size must be between 6 and 32");
    }

    @Test
    void register_User_With_More_Than_ThirtyTwo_Characters_In_The_Password_Test() throws Exception {
        user.setPassword("1234567890aB@!5678901234567890000");
        genericStructureToRegisterTest(user, 400, "Field password has an error: size must be between 6 and 32");
    }

    @Test
    void register_User_With_Incorrect_Password_Test() throws Exception {
        user.setPassword("EXAMPLE1aavb");
        genericStructureToRegisterTest(user, 400, "Field password has an error: Only letters, numbers and special characters are allowed");
    }

    @Test
    void register_User_With_Less_Than_Eight_Characters_In_The_WalletAddress_Test() throws Exception {
        user = UserFactory.userWithLargeWalletAddress();
        genericStructureToRegisterTest(user, 400, "Field digitalWalletAddress has an error: size must be between 8 and 8");
    }

    @Test
    void register_User_With_More_Than_Eight_Characters_In_The_WalletAddress_Test() throws Exception {
        user = UserFactory.userWithShortWalletAddress();
        genericStructureToRegisterTest(user, 400, "Field digitalWalletAddress has an error: size must be between 8 and 8");
    }
}