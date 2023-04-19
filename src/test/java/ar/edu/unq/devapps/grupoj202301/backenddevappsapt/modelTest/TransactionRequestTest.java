package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.modelTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.CryptoActiveFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.TransactionDataFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.TransactionRequestFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionData;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TransactionRequestTest {
    public final TransactionRequest transactionRequest = TransactionRequestFactory.anyTransactionRequest();
    @Test
    void get_Id_Test() {
        Assertions.assertEquals(1L, transactionRequest.getId());
    }

    @Test
    void get_CryptoActive_Test() {
        final CryptoActive cryptoActive = CryptoActiveFactory.anyCryptoActive();
        transactionRequest.setCryptoActive(cryptoActive);
        Assertions.assertEquals(cryptoActive, transactionRequest.getCryptoActive());
    }

    @Test
    void get_Amount_Test() {
        Assertions.assertEquals(new BigDecimal("50"), transactionRequest.getAmount());
    }

    @Test
    void get_Date_Test() {
        LocalDateTime dateTime = LocalDateTime.now();
        transactionRequest.setDate(dateTime);
        Assertions.assertEquals(dateTime, transactionRequest.getDate());
    }

    @Test
    void get_TransactionData_Test() {
        final TransactionData transactionData = TransactionDataFactory.anyTransactionDataPurchase();
        transactionRequest.setTransactionData(transactionData);
        Assertions.assertEquals(transactionData, transactionRequest.getTransactionData());
    }
}