package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.Role;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;

public class UserFactory {
    public static User anyUser() {
        return new User("example@example.com", "00000000", "example", "example", "exampleAddres", "exampleP1!", "0123456789012345678900");
    }

    public static User userWithShortWalletAddress() {
        return new User("example@example.com", "1A", "example", "example", "exampleAddres", "exampleP1!", "0123456789012345678900");
    }

    public static User userWithLargeWalletAddress() {
        return new User("example@example.com", "1A1A1A1A1A", "example", "example", "exampleAddres", "exampleP1!", "0123456789012345678900");
    }

    public static User anyUserWithAnotherEmail() {
        return new User("anotherEmail@example.com", "00000001", "example", "example", "exampleAddres", "exampleP2!", "0123456789012345678900");
    }

    public static User anyUserRoleUser() {
        User user = new User("user1@example.com", "00000011", "example", "example", "exampleAddres", "exampleP2!", "0123456789012345678111");
        user.getRoles().add(new Role("USER"));
        return user;
    }

    public static User anyUserRoleAdmin() {
        User user = new User("userAdmin@example.com", "00000111", "example", "example", "exampleAddres", "exampleP2!", "0123456789012345671111");
        user.getRoles().add(new Role("ADMIN"));
        return user;
    }
}
