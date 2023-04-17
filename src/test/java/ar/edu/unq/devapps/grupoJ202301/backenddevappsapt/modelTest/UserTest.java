package ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.modelTest;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoJ202301.backenddevappsapt.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    private final User user = UserFactory.anyUser();
    @Test
    public void get_Email_Test() {
        Assertions.assertEquals("example@example.com", user.getEmail());
    }
    @Test
    public void get_Name_Test() {
        Assertions.assertEquals("example", user.getName());
    }
    @Test
    public void get_Surname_Test() {
        Assertions.assertEquals("example", user.getSurname());
    }
    @Test
    public void get_Address_Test() {
        Assertions.assertEquals("exampleAddres", user.getAddress());
    }
    @Test
    public void get_Password_Test() {
        Assertions.assertEquals("exampleP1!", user.getPassword());
    }
    @Test
    public void get_CVU_Test() {
        Assertions.assertEquals("0123456789012345678900", user.getCvu());
    }
    @Test
    public void get_WalletAddress_Test() {
        Assertions.assertEquals("00000000", user.getWalletAddress());
    }
    @Test
    public void build_test() { Assertions.assertInstanceOf( User.class, User.builder().build());}
}
