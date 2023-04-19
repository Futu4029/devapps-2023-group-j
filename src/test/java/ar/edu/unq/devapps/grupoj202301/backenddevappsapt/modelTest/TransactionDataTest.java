package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.modelTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.TransactionDataFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionData;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enumModel.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class TransactionDataTest {
    private final TransactionData transactionDataPurchase = TransactionDataFactory.anyTransactionDataPurchase();
    @Test
    void get_Id_Test() {
        Assertions.assertEquals(1L,transactionDataPurchase.getId());
    }

    @Test
    void get_CryptoName_Test() {
        Assertions.assertEquals("exampleCoin", transactionDataPurchase.getCryptoName());
    }

    @Test
    void get_CryptoAmount_Test() {
        Assertions.assertEquals(new BigDecimal("10"), transactionDataPurchase.getCryptoAmount());
    }

    @Test
    void get_PesosAmount_Test() {
        Assertions.assertEquals(new BigDecimal("100"), transactionDataPurchase.getPesosAmount());
    }

    @Test
    void get_CreationDate_Test() {
        LocalDateTime date = LocalDateTime.now();
        transactionDataPurchase.setCreationDate(date);
        Assertions.assertEquals(date, transactionDataPurchase.getCreationDate());
    }

    @Test
    void get_UserOwner_Test() {
        final User user = UserFactory.anyUser();
        transactionDataPurchase.setUserOwner(user);
        Assertions.assertEquals(user, transactionDataPurchase.getUserOwner());
    }

    @Test
    void get_TransactionType_Test() {
        Assertions.assertEquals(TransactionType.PURCHASE, transactionDataPurchase.getTransactionType());
    }
}
