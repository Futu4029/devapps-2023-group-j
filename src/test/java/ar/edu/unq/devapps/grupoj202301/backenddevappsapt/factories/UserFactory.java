package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;

public class UserFactory {
    public static User anyUser() {
        return new User("example@example.com", "00000000", "example", "example", "exampleAddres", "exampleP1!", "0123456789012345678900");
    }
}
