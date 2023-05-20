package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.modelTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.CryptoActiveFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.TransactionRequestFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class TransactionRequestTest {
    final TransactionRequest transactionRequest = TransactionRequestFactory.anyTransactionRequest();
    final CryptoActive cryptoActive = CryptoActiveFactory.anyCryptoActive();
    @Test
    void get_Id_Test() {
        Assertions.assertEquals(1L, transactionRequest.getId());
    }

    @Test
    void get_CryptoActive_Test() {
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

   /* @Test
    void get_TransactionData_Test() {
        transactionRequest.setTransactionData(transactionData);
        Assertions.assertEquals(transactionData, transactionRequest.getTransactionData());
    }

    @Test
    void get_TransactionReq_Constructor_Test() {
        LocalDateTime dateTime = LocalDateTime.now();
        TransactionRequest transactionRequestForTest = new TransactionRequest(cryptoActive, "100", dateTime, transactionData);
        Assertions.assertEquals(cryptoActive, transactionRequestForTest.getCryptoActive());
        Assertions.assertEquals(new BigDecimal("100"), transactionRequestForTest.getAmount());
        Assertions.assertEquals(dateTime, transactionRequestForTest.getDate());
        Assertions.assertEquals(transactionData, transactionRequestForTest.getTransactionData());
    }*/
}