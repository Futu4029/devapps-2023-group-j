package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.unitTest.webServiceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.CryptoActiveVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoActivePersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoCoinPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.DigitalWalletPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.TransactionRequestPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionRequestWebServiceTest {

    @Autowired
    private MockMvc mockMvc;
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
    CryptoCoinService cryptoCoinService;

    @Autowired
    UserService userService;

    TransactionRequestVolumeInfo transactionRequestVolumeInfo;
    TransactionRequestVolumeInfo transactionRequestVolumeInfoWithEmptyList;
    LocalDateTime yesterday;
    LocalDateTime tomorrow;
    String cryptoCoinName;
    CryptoActiveVolumeInfo cryptoDTO;
    BigDecimal cryptoAmount;
    User user = UserFactory.anyUser();
    IntentionPSDTO intention;


    @BeforeEach
    void setUp() throws IOException {
        userService.register(user);
        intention =  new IntentionPSDTO("example@example.com", "BNBUSDT", new BigDecimal("11.0"),new BigDecimal("100"), TransactionType.PURCHASE);
        yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.NOON);
        tomorrow = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.NOON);
        //----- transactionRequestVolumeInfo with data ------
        cryptoCoinName = "AAVEUSDT";
        cryptoAmount = new BigDecimal("100");
        cryptoDTO = new CryptoActiveVolumeInfo(
                cryptoCoinName,
                cryptoAmount,
                cryptoCoinService.getCryptoCoinCotizationByName(cryptoCoinName),
                cryptoCoinService.getTheValueOfAnAmountOfCryptoCoinInPesos(cryptoCoinName, cryptoAmount)
        );
        List<CryptoActiveVolumeInfo> cryptoActivesList = List.of(cryptoDTO);
        transactionRequestVolumeInfo = new TransactionRequestVolumeInfo(
                LocalDateTime.now(),
                new BigDecimal("1"),
                new BigDecimal("0.1"),
                cryptoActivesList);
        //----- transactionRequestVolumeInfo empty ------
        transactionRequestVolumeInfoWithEmptyList = new TransactionRequestVolumeInfo(
                LocalDateTime.now(),
                new BigDecimal("0"),
                new BigDecimal("0"),
                new ArrayList<>());

    }

    @AfterEach
    void cleanUp() {
        mockMvc = null;
    }

    private void saveIntention(IntentionPSDTO intention, int statusCode, Long id) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String intentionToRegister = objectMapper.writeValueAsString(intention);
        var result = mockMvc.perform(post("/transaction/createIntentionPS")
                        .content(intentionToRegister)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(statusCode))
                        .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), id.toString());
    }

    private void genericStructureToGetVolumeOfOperationTest(String email, LocalDateTime startDate, LocalDateTime endDate, int statusCode, TransactionRequestVolumeInfo response) throws Exception {
        var result = mockMvc.perform(get("/transaction/betweenDates/{email}/{startDate}/{endDate}", email, startDate, endDate)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(statusCode))
                        .andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode responseValue = objectMapper.readTree(result.getResponse().getContentAsString());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        response.setDate(LocalDateTime.parse(responseValue.get("date").asText(), formatter));
        Assertions.assertEquals(result.getResponse().getContentAsString()  , objectMapper.writeValueAsString(response));
    }

    @Test
    void register_intention() throws Exception{
        saveIntention(intention, 200,  1L);
    }

    @Test
    void register_intention_and_get_volume_of_transaction_empty() throws Exception{
        saveIntention(intention, 200,  1L);
        genericStructureToGetVolumeOfOperationTest(user.getEmail(), yesterday, tomorrow, 200, transactionRequestVolumeInfoWithEmptyList);
    }

    @Test
    void register_intention_and_get_volume_of_expected_transaction() throws Exception{
        saveIntention(intention, 200,  1L);
        //ACA MÉTODO DE PROCESS. PARA PASAR LA TRANSACTION A ACEPTADA
        // TODO -> TransactionService.process(transactionRequestVolumeInfo, user2);
        genericStructureToGetVolumeOfOperationTest(user.getEmail(), yesterday, tomorrow, 200, transactionRequestVolumeInfo);
    }

}