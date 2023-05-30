package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.integrationTest.webServiceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionDataDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.ActionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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
    UserPersistence userPersistence;

    @Autowired
    UserService userService;

    TransactionRequestVolumeInfo transactionRequestVolumeInfoWithEmptyList;
    LocalDateTime yesterday;
    LocalDateTime tomorrow;
    String cryptoCoinName;
    BigDecimal cryptoAmount;
    User user = UserFactory.anyUser();
    User anyOtherUser = UserFactory.anyUserWithAnotherEmail();
    IntentionPSDTO intentionPurchase;
    IntentionPSDTO intentionSell;


    @BeforeEach
    void setUp()  {
        transactionRequestPersistence.deleteAllInBatch();
        userPersistence.deleteAllInBatch();
        userService.register(user);
        userService.register(anyOtherUser);
        intentionPurchase =  new IntentionPSDTO("example@example.com", "BNBUSDT", new BigDecimal("11.0"),new BigDecimal("100"), TransactionType.PURCHASE);
        intentionSell =  new IntentionPSDTO("example@example.com", "BNBUSDT", new BigDecimal("11.0"),new BigDecimal("100"), TransactionType.SELL);
        yesterday = LocalDateTime.of(LocalDateTime.now().minusDays(1).toLocalDate(), LocalTime.NOON);
        tomorrow = LocalDateTime.of(LocalDateTime.now().plusDays(1).toLocalDate(), LocalTime.NOON);
        //----- transactionRequestVolumeInfo with data ------
        cryptoCoinName = "AAVEUSDT";
        cryptoAmount = new BigDecimal("100");
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

    private void interactWithTransaction(TransactionDataDTO transactionDataDTO, int statusCode, String expected) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String intentionToRegister = objectMapper.writeValueAsString(transactionDataDTO);
        var result = mockMvc.perform(post("/transaction/interactWithATransactionRequest")
                        .content(intentionToRegister)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(statusCode))
                .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), expected);
    }

    private void confirmTransaction(TransactionDataDTO transactionDataDTO, int statusCode, String expected) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String intentionToRegister = objectMapper.writeValueAsString(transactionDataDTO);
        var result = mockMvc.perform(post("/transaction/confirmTransaction")
                        .content(intentionToRegister)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(statusCode))
                .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), expected);
    }

    private void cancelTransaction(TransactionDataDTO transactionDataDTO, int statusCode, String expected) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String intentionToRegister = objectMapper.writeValueAsString(transactionDataDTO);
        var result = mockMvc.perform(post("/transaction/cancel")
                        .content(intentionToRegister)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(statusCode))
                .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), expected);
    }

    private void structureToGetVolumeOfOperationTest(String email, LocalDateTime startDate, LocalDateTime endDate, int statusCode, TransactionRequestVolumeInfo response) throws Exception {
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

    private void structureToGetTransactionByState(String email, int statusCode, List<TransactionRequest> responseList) throws Exception {
        var result = mockMvc.perform(get("/transaction/getActivesTransactions/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(statusCode))
                        .andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode responseValue = objectMapper.readTree(result.getResponse().getContentAsString());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        for(TransactionRequest t : responseList){
            t.setDate(LocalDateTime.parse(responseValue.get(0).get("date").asText(), formatter));
            t.setCreationDate(LocalDateTime.parse(responseValue.get(0).get("creationDate").asText(), formatter));
        }
        String expected = objectMapper.writeValueAsString(responseList);
        Assertions.assertEquals(result.getResponse().getContentAsString(), expected);
    }

    @Test
    @DirtiesContext
    void register_intention() throws Exception{
        saveIntention(intentionPurchase, 200,  1L);
    }

    @Test
    @DirtiesContext
    void register_intention_and_get_volume_of_transaction_empty() throws Exception{
        saveIntention(intentionPurchase, 200,  1L);
        structureToGetVolumeOfOperationTest(user.getEmail(), yesterday, tomorrow, 200, transactionRequestVolumeInfoWithEmptyList);
    }

    @Test
    @DirtiesContext
    void register_intention_confirm_and_get_volume_of_expected_transaction() throws Exception{
        saveIntention(intentionPurchase, 200,  1L);
        TransactionRequest transactionRequest = transactionRequestService.getTransactionsById(1L);
        transactionRequestService.interactWithATransactionRequest(new TransactionDataDTO(anyOtherUser.getEmail(), transactionRequest.getId()));
        transactionRequestService.confirmReception(new TransactionDataDTO(user.getEmail(), transactionRequest.getId()));
        structureToGetVolumeOfOperationTest(user.getEmail(), yesterday, tomorrow, 200, transactionRequestService.calculateTransactionRequestVolumeInfo(List.of(transactionRequest)));
    }

    @Test
    @DirtiesContext
    void register_2_intention_confirm_and_get_volume_of_expected_transaction() throws Exception{
        saveIntention(intentionPurchase, 200,  1L);
        saveIntention(intentionPurchase, 200,  2L);
        TransactionRequest transactionRequest = transactionRequestService.getTransactionsById(1L);
        TransactionRequest transactionRequest2 = transactionRequestService.getTransactionsById(2L);
        transactionRequestService.interactWithATransactionRequest(new TransactionDataDTO(anyOtherUser.getEmail(), transactionRequest.getId()));
        transactionRequestService.interactWithATransactionRequest(new TransactionDataDTO(anyOtherUser.getEmail(), transactionRequest2.getId()));
        transactionRequestService.confirmReception(new TransactionDataDTO(user.getEmail(), transactionRequest.getId()));
        transactionRequestService.confirmReception(new TransactionDataDTO(user.getEmail(), transactionRequest2.getId()));
        structureToGetVolumeOfOperationTest(user.getEmail(), yesterday, tomorrow, 200, transactionRequestService.calculateTransactionRequestVolumeInfo(List.of(transactionRequest, transactionRequest2)));
    }

    /*@Test
    @DirtiesContext
    void get_transaction_by_state_with_state_() throws Exception {
        saveIntention(intentionPurchase, 200, 1L);
        TransactionRequest transactionRequest = transactionRequestService.getTransactionsById(1L);
        structureToGetTransactionByState(user.getEmail(), 200, List.of(transactionRequest));
    }*/

    @Test
    @DirtiesContext
    void interact_with_transaction_purchase() throws Exception {
        saveIntention(intentionPurchase, 200, 1L);
        interactWithTransaction(new TransactionDataDTO(anyOtherUser.getEmail(), 1L), 200, ActionType.MAKETHETRANSFER.name());
    }

    @Test
    @DirtiesContext
    void confirm_a_transaction() throws Exception {
        saveIntention(intentionPurchase, 200, 1L);
        TransactionRequest transactionRequest = transactionRequestService.getTransactionsById(1L);
        transactionRequestService.interactWithATransactionRequest(new TransactionDataDTO(anyOtherUser.getEmail(), transactionRequest.getId()));
        confirmTransaction(new TransactionDataDTO(anyOtherUser.getEmail(), 1L), 200, TransactionState.ACCEPTED.name());
    }

    @Test
    @DirtiesContext
    void cancel_a_transaction() throws Exception {
        saveIntention(intentionPurchase, 200, 1L);
        TransactionRequest transactionRequest = transactionRequestService.getTransactionsById(1L);
        transactionRequestService.interactWithATransactionRequest(new TransactionDataDTO(anyOtherUser.getEmail(), transactionRequest.getId()));
        cancelTransaction(new TransactionDataDTO(user.getEmail(), 1L), 200, TransactionState.CANCELLED.name());
    }

    @Test
    @DirtiesContext
    void owner_cancels_a_transaction() throws Exception {
        saveIntention(intentionPurchase, 200, 1L);
        TransactionRequest transactionRequest = transactionRequestService.getTransactionsById(1L);
        transactionRequestService.interactWithATransactionRequest(new TransactionDataDTO(anyOtherUser.getEmail(), transactionRequest.getId()));
        cancelTransaction(new TransactionDataDTO(user.getEmail(), 1L), 200, TransactionState.CANCELLED.name());
    }

    @Test
    @DirtiesContext
    void other_cancels_a_transaction() throws Exception {
        saveIntention(intentionPurchase, 200, 1L);
        TransactionRequest transactionRequest = transactionRequestService.getTransactionsById(1L);
        transactionRequestService.interactWithATransactionRequest(new TransactionDataDTO(anyOtherUser.getEmail(), transactionRequest.getId()));
        cancelTransaction(new TransactionDataDTO(anyOtherUser.getEmail(), 1L), 200, TransactionState.ACTIVE.name());
    }

}