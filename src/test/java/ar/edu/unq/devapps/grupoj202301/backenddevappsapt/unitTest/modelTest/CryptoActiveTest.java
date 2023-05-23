package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.unitTest.modelTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.CryptoActiveFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.DigitalWalletFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.DigitalWallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class CryptoActiveTest {
    final CryptoActive cryptoActive = CryptoActiveFactory.anyCryptoActive();
    final DigitalWallet digitalWallet = DigitalWalletFactory.anyDigitalWallet();

    @Test
    void get_Constructor_Test() {
        Assertions.assertEquals(1L, cryptoActive.getId());
        Assertions.assertEquals("ExampleCoin", cryptoActive.getCryptoCoinName());
        Assertions.assertEquals(new BigDecimal("10"), cryptoActive.getAmountOfCryptoCoin());
    }
}