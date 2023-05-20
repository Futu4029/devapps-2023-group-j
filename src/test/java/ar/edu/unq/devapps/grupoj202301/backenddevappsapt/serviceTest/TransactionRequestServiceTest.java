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
public class TransactionRequestServiceTest {

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
        user = UserFactory.anyUser();
        userWithAnotherEmail = UserFactory.anyUserWithAnotherEmail();
    }

    @Test
    void volumeOperatedBetweenDates (){
        LocalDateTime yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.NOON);
        LocalDateTime tomorrow = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.NOON);
        TransactionRequest transactionRequestAccepted = TransactionRequestFactory.anyTransactionRequest();
        transactionRequestAccepted.setTransactionState(TransactionState.ACCEPTED);
        transactionRequestService.save(transactionRequestAccepted);
        TransactionRequestVolumeInfo result = transactionRequestService.volumeOperatedBetweenDates(user.getEmail(), yesterday, tomorrow);
        LocalDateTime date = LocalDateTime.now();
        BigDecimal totalDollarAmount = new BigDecimal("1.00");
        BigDecimal totalPesosAmount = new BigDecimal("0.01");

        Assertions.assertEquals(1, result.getCryptoActiveVolumeInfoList().size());
        Assertions.assertEquals(totalDollarAmount, result.getDollarAmount());
        Assertions.assertEquals(totalPesosAmount, result.getPesosAmount());
        Assertions.assertEquals(date, result.getDate());
    }
    @Test
    void volumeOperatedBetweenDatesWithMoreRequest (){
        LocalDateTime yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.NOON);
        LocalDateTime tomorrow = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.NOON);
        TransactionRequest transactionRequestAccepted = TransactionRequestFactory.anyTransactionRequest();
        transactionRequestAccepted.setTransactionState(TransactionState.ACCEPTED);
        TransactionRequest transactionRequestAccepted2 = TransactionRequestFactory.anyTransactionRequest();
        transactionRequestAccepted2.setTransactionState(TransactionState.ACCEPTED);
        transactionRequestService.save(transactionRequestAccepted);
        transactionRequestService.save(transactionRequestAccepted2);
        TransactionRequestVolumeInfo result = transactionRequestService.volumeOperatedBetweenDates(user.getEmail(), yesterday, tomorrow);
        LocalDateTime date = LocalDateTime.now();
        BigDecimal totalDollarAmount = new BigDecimal("2.00");
        BigDecimal totalPesosAmount = new BigDecimal("0.02");

        Assertions.assertEquals(2, result.getCryptoActiveVolumeInfoList().size());
        Assertions.assertEquals(totalDollarAmount, result.getDollarAmount());
        Assertions.assertEquals(totalPesosAmount, result.getPesosAmount());
        Assertions.assertEquals(date, result.getDate());
    }
}

