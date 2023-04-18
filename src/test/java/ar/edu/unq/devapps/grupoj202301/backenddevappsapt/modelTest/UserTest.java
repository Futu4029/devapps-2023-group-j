package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.modelTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {
    private final User user = UserFactory.anyUser();
    @Test
    void get_Email_Test() {
        Assertions.assertEquals("example@example.com", user.getEmail());
    }
    @Test
    void get_Name_Test() {
        Assertions.assertEquals("example", user.getName());
    }
    @Test
    void get_Surname_Test() {
        Assertions.assertEquals("example", user.getSurname());
    }
    @Test
    void get_Address_Test() {
        Assertions.assertEquals("exampleAddres", user.getAddress());
    }
    @Test
    void get_Password_Test() {
        Assertions.assertEquals("exampleP1!", user.getPassword());
    }
    @Test
    void get_CVU_Test() {
        Assertions.assertEquals("0123456789012345678900", user.getCvu());
    }
    @Test
    void get_WalletAddress_Test() {
        Assertions.assertEquals("00000000", user.getWalletAddress());
    }
    @Test
    void build_test() { Assertions.assertInstanceOf( User.class, User.builder().build());}
}
