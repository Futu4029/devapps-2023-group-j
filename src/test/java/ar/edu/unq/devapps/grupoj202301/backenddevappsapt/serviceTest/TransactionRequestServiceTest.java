package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.serviceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest(properties = "spring.config.name=application-test")
public class TransactionRequestServiceTest {

    @Autowired
    TransactionRequestService transactionRequestService;

    @Autowired
    TransactionRequestPersistence transactionRequestPersistence;

    @Autowired
    CryptoActivePersistence cryptoActivePersistence;

    @Autowired
    CryptoCoinPersistence cryptoCoinPersistence;

    @Autowired
    DigitalWalletPersistence digitalWalletPersistence;

    @Autowired
    UserService userService;

    @Autowired
    UserPersistence userPersistence;

    private TransactionRequest transactionRequest;
    private User user = UserFactory.anyUser();
    private CryptoActive cryptoActive = CryptoActiveFactory.anyCryptoActive();
    private CryptoCoin cryptoCoin = CryptoCoinFactory.anyCryptoCoin();
    private DigitalWallet digitalWallet = DigitalWalletFactory.anyDigitalWallet();

    @BeforeEach
    void setup(){
        userService.register(user);
        cryptoCoinPersistence.save(cryptoCoin);
        digitalWalletPersistence.save(digitalWallet);
        cryptoActivePersistence.save(cryptoActive);
        transactionRequestPersistence.save(transactionRequest = TransactionRequestFactory.anyTransactionRequest());
        user = UserFactory.anyUser();
    }
    @AfterEach
    void clear(){
        transactionRequestPersistence.deleteAll();
        userPersistence.deleteAll();
    }

    @Test
    void volumeOperatedBetweenDates () throws IOException {
        LocalDateTime yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.NOON);
        LocalDateTime tomorrow = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.NOON);
        TransactionRequest transactionRequestAccepted = TransactionRequestFactory.anyTransactionRequest();
        transactionRequestAccepted.setTransactionState(TransactionState.ACCEPTED);
        transactionRequestPersistence.save(transactionRequestAccepted);
        TransactionRequestVolumeInfo result = transactionRequestService.volumeOperatedBetweenDates(user.getEmail(), yesterday, tomorrow);
        LocalDateTime date = LocalDateTime.now();
        BigDecimal totalDollarAmount = new BigDecimal("10.00");
        BigDecimal totalPesosAmount = new BigDecimal("0.01000000");

        Assertions.assertEquals(1, result.getCryptoActiveVolumeInfoList().size());
        Assertions.assertEquals(totalDollarAmount, result.getDollarAmount());
        Assertions.assertEquals(totalPesosAmount, result.getPesosAmount());
        Assertions.assertEquals(date, result.getDate());
    }
    @Test
    void volumeOperatedBetweenDatesWithMoreRequest () throws IOException {
        LocalDateTime yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.NOON);
        LocalDateTime tomorrow = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.NOON);
        TransactionRequest transactionRequestAccepted = TransactionRequestFactory.anyTransactionRequest();
        transactionRequestAccepted.setTransactionState(TransactionState.ACCEPTED);
        TransactionRequest transactionRequestAccepted2 = TransactionRequestFactory.anyTransactionRequest();
        transactionRequestAccepted2.setTransactionState(TransactionState.ACCEPTED);
        transactionRequestPersistence.save(transactionRequestAccepted);
        transactionRequestPersistence.save(transactionRequestAccepted2);
        TransactionRequestVolumeInfo result = transactionRequestService.volumeOperatedBetweenDates(user.getEmail(), yesterday, tomorrow);
        LocalDateTime date = LocalDateTime.now();
        BigDecimal totalDollarAmount = new BigDecimal("20.00");
        BigDecimal totalPesosAmount = new BigDecimal("0.02000000");

        Assertions.assertEquals(2, result.getCryptoActiveVolumeInfoList().size());
        Assertions.assertEquals(totalDollarAmount, result.getDollarAmount());
        Assertions.assertEquals(totalPesosAmount, result.getPesosAmount());
        Assertions.assertEquals(date, result.getDate());
    }
}

