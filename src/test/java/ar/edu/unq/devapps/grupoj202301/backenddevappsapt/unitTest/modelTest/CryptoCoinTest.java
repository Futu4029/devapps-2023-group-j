package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.unitTest.modelTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.CryptoCoinFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class CryptoCoinTest {
    final CryptoCoin cryptoCoin = CryptoCoinFactory.anyCryptoCoin();
    @Test
    void get_Name_Test() {
        Assertions.assertEquals("ExampleCoin", cryptoCoin.getName());
    }

    @Test
    void get_Value_Test() {
        Assertions.assertEquals(new BigDecimal("100"), cryptoCoin.getPrice());
    }

    @Test
    void get_QuotationDate_Test() {
        LocalDateTime localDateTime = LocalDateTime.now();
        cryptoCoin.setQuotationDate(localDateTime);
        Assertions.assertEquals(localDateTime, cryptoCoin.getQuotationDate());
    }
}