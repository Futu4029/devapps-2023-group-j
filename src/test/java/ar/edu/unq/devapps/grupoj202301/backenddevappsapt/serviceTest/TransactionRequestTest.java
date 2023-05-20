package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.serviceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.TransactionRequestFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
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

    private TransactionRequest transactionRequest;
    private User user;

    @BeforeEach
    void setup(){
        transactionRequestService.save(transactionRequest = TransactionRequestFactory.anyTransactionRequest());
        user = UserFactory.anyUser();
    }

    @Test
    void volumeOperatedBetweenDates (){
        transactionRequestService.save(transactionRequest = TransactionRequestFactory.anyTransactionRequestOneWeekLater());
        LocalDateTime yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.NOON);
        LocalDateTime tomorrow = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.NOON);
        BigDecimal amount = new BigDecimal("100");
        BigDecimal dollarAmount = new BigDecimal("1");
        BigDecimal pesosAmount = new BigDecimal("0.001");
        TransactionRequestVolumeInfo transactionRequestVolumeInfo = transactionRequestService.volumeOperatedBetweenDates(user.getEmail(), yesterday, tomorrow);
        Assertions.assertTrue(transactionRequestVolumeInfo.getCryptoActiveVolumeInfoList().size() == 1);
    }
}
