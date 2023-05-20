package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.serviceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoActivePersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.DigitalWalletPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionRequestTest {

    @Autowired
    TransactionRequestService transactionRequestService;

    @Autowired
    CryptoActivePersistence cryptoActivePersistence;

    @Autowired
    CryptoCoinPersistence cryptoCoinPersistence;

    @Autowired
    DigitalWalletPersistence digitalWalletPersistence;

    @Autowired
    UserService userService;

    private TransactionRequest transactionRequestAccepted;
    private TransactionRequest transactionRequest;
    private User user = UserFactory.anyUser();
    private User userWithAnotherEmail = UserFactory.anyUserWithAnotherEmail();
    private CryptoActive cryptoActive = CryptoActiveFactory.anyCryptoActive();
    private CryptoCoin cryptoCoin = CryptoCoinFactory.anyCryptoCoin();
    private DigitalWallet digitalWallet = DigitalWalletFactory.anyDigitalWallet();

    @BeforeEach
    void setup(){
        userService.register(user);
        cryptoCoinPersistence.save(cryptoCoin);
        digitalWalletPersistence.save(digitalWallet);
        cryptoActivePersistence.save(cryptoActive);
        transactionRequestService.save(transactionRequest = TransactionRequestFactory.anyTransactionRequest());
        transactionRequestAccepted = TransactionRequestFactory.anyTransactionRequest();
        transactionRequestAccepted.setTransactionState(TransactionState.ACCEPTED);
        transactionRequestService.save(transactionRequestAccepted);
        user = UserFactory.anyUser();
        userWithAnotherEmail = UserFactory.anyUserWithAnotherEmail();
    }

    @Test
    void volumeOperatedBetweenDates (){
        transactionRequestAccepted = TransactionRequestFactory.anyTransactionRequest();
        transactionRequestAccepted.setTransactionState(TransactionState.ACCEPTED);
        transactionRequestService.save(transactionRequest);
        LocalDateTime yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.NOON);
        LocalDateTime tomorrow = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.NOON);
        BigDecimal amount = new BigDecimal("100");
        BigDecimal dollarAmount = new BigDecimal("1");
        BigDecimal pesosAmount = new BigDecimal("0.001");
        TransactionRequestVolumeInfo transactionRequestVolumeInfo = transactionRequestService.volumeOperatedBetweenDates(user.getEmail(), yesterday, tomorrow);
        Assertions.assertTrue(transactionRequestVolumeInfo.getCryptoActiveVolumeInfoList().size() == 1);
    }
}
