package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.unitTest.modelTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.DigitalWalletFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.DigitalWallet;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DigitalWalletTest {
    final DigitalWallet digitalWallet = DigitalWalletFactory.anyDigitalWallet();
    @Test
    void get_Address_Test() {
        Assertions.assertEquals("00000000",digitalWallet.getAddress());
    }

    @Test
    void get_Owner_Test() {
        final User anyUser = UserFactory.anyUser();
        digitalWallet.setOwner(anyUser);
        Assertions.assertEquals(anyUser,digitalWallet.getOwner());
    }

    @Test
    void get_CryptoCoinsAcquired_EmptyList_Test() {
        Assertions.assertTrue(digitalWallet.getCryptoCoinsAcquired().isEmpty());
    }
}