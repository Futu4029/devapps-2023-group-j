package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.modelTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.CryptoActiveFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.CryptoCoinFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.DigitalWalletFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.DigitalWallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class CryptoActiveTest {
    final CryptoActive cryptoActive = CryptoActiveFactory.anyCryptoActive();
    final DigitalWallet digitalWallet = DigitalWalletFactory.anyDigitalWallet();
    @Test
    void get_Id_Test() {
        Assertions.assertEquals(1L, cryptoActive.getId());
    }

    @Test
    void get_Coin_Test() {
        final CryptoCoin cryptoCoin = CryptoCoinFactory.anyCryptoCoin();
        cryptoActive.setCoin(cryptoCoin);
        Assertions.assertEquals(cryptoCoin, cryptoActive.getCoin());
    }

    @Test
    void get_DigitalWallet_Test() {
        cryptoActive.setDigitalWallet(digitalWallet);
        Assertions.assertEquals(digitalWallet, cryptoActive.getDigitalWallet());
    }

    @Test
    void get_Value_Test() {
        Assertions.assertEquals(new BigDecimal("10"), cryptoActive.getPrice());
    }

    @Test
    void get_Constructor_Test() {
        CryptoActive cryptoActive = new CryptoActive("ExampleCoin", digitalWallet, "10");
        Assertions.assertEquals("ExampleCoin", cryptoActive.getCoin().getName());
        Assertions.assertEquals(digitalWallet, cryptoActive.getDigitalWallet());
        Assertions.assertEquals(new BigDecimal("10"), cryptoActive.getPrice());
    }
}