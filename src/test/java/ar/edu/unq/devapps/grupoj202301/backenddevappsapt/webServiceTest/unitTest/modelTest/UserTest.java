package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.unitTest.modelTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {
    private final User user = UserFactory.anyUser();
    @Test
    void get_Email_Test() {
        Assertions.assertEquals("example@example.com", user.getEmail());
        user.setEmail("ejemplo@ejemplo.com");
        Assertions.assertEquals("ejemplo@ejemplo.com", user.getEmail());
    }
    @Test
    void get_Name_Test() {
        Assertions.assertEquals("example", user.getName());
        user.setName("ejemplo");
        Assertions.assertEquals("ejemplo", user.getName());
    }
    @Test
    void get_Surname_Test() {
        Assertions.assertEquals("example", user.getSurname());
        user.setSurname("ejemplo");
        Assertions.assertEquals("ejemplo", user.getSurname());
    }
    @Test
    void get_Address_Test() {
        Assertions.assertEquals("exampleAddres", user.getAddress());
        user.setAddress("ejemploDireccion");
        Assertions.assertEquals("ejemploDireccion", user.getAddress());
    }
    @Test
    void get_Password_Test() {
        Assertions.assertEquals("exampleP1!", user.getPassword());
        user.setPassword("ejemploB1!!");
        Assertions.assertEquals("ejemploB1!!", user.getPassword());
    }
    @Test
    void get_CVU_Test() {
        Assertions.assertEquals("0123456789012345678900", user.getCvu());
        user.setCvu("0123456789012345678901");
        Assertions.assertEquals("0123456789012345678901", user.getCvu());
    }
    @Test
    void get_WalletAddress_Test() {
        Assertions.assertEquals("00000000", user.getWalletAddress());
        user.setWalletAddress("00000001");
        Assertions.assertEquals("00000001", user.getWalletAddress());
    }
    @Test
    void build_test() { Assertions.assertInstanceOf( User.class, User.builder().build());}

    @Test
    void get_ID_Test() {
        Assertions.assertEquals("example@example.com", user.getId());
    }
}
