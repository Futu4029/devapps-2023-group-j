package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleUserInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.auth.DtoLogin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.IntentionPurchaseSaleFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import java.io.IOException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntentionPurchaseSaleWebServiceTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";
    private final User userOne = UserFactory.anyUser();
    private final User userTwo = UserFactory.anyUserWithAnotherEmail();
    private final DtoLogin userLoginOne = new DtoLogin(userOne.getEmail(), userOne.getPassword());
    private final DtoLogin userLoginTwo = new DtoLogin(userTwo.getEmail(), userTwo.getPassword());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthHelper authHelper;

    @BeforeEach
    void setUp() {
       authHelper.setData(HTTP_LOCALHOST, port, restTemplate);
    }
    
    @Test
    @DirtiesContext
    void create_intention() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        IntentionPurchaseSale anyIntention = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        HttpEntity<IntentionPurchaseSale> requestEntity = new HttpEntity<>(anyIntention, headers);
        ResponseEntity<IntentionPurchaseSale> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, IntentionPurchaseSale.class);
        IntentionPurchaseSale createdIntention = response.getBody();
        assertEquals(7, createdIntention.getId());
    }

    @Test
    @DirtiesContext
    void create_intention_with_a_non_existent_crypto_coin_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        IntentionPurchaseSale anyIntention = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        anyIntention.setCryptoCoinName("ExampleCoin");
        HttpEntity<IntentionPurchaseSale> requestEntity = new HttpEntity<>(anyIntention, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id ExampleCoin is not present", response.getBody());
    }

    @Test
    @DirtiesContext
    void create_intention_with_a_non_logged_user_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        IntentionPurchaseSale anyIntention = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        HttpEntity<IntentionPurchaseSale> requestEntity = new HttpEntity<>(anyIntention, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Assertions.assertEquals("{\"Error\":\"unavailable session\"}", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_user_is_owner_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), String.class);
        String message =  response.getBody();
        assertEquals("The operation was successfully canceled. You have lost 20 points.", message);
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_user_is_not_bound_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginTwo));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The user entered is not related to this intention.", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_no_longer_active_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/4";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(headers), String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: Intent is not active.", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_unregistered_intention_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/18";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(headers), String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id 18 is not present", response.getBody());
    }

    @Test
    @DirtiesContext
    void proceed_intention_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/proceed/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginTwo));
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(headers), String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Congratulations, the interaction with the intention was successful. " +
                "Wait for the other user to confirm.", response.getBody());
    }

    @Test
    @DirtiesContext
    void proceed_intention_user_owner_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/proceed/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(headers), String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: You cannot proceed on your own intention.", response.getBody());
    }

    @Test
    @DirtiesContext
    void proceed_intention_when_no_longer_active_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/proceed/3";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginTwo));
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(headers), String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: Intent is not active.", response.getBody());
    }

    @Test
    @DirtiesContext
    void confirm_intention_test() throws IOException {
        String urlProceed = HTTP_LOCALHOST + port + "/intention/interaction/proceed/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginTwo));
        var httpEntity = new HttpEntity<>(headers);
        restTemplate.postForEntity(urlProceed, httpEntity, String.class);
        String urlConfirm = HTTP_LOCALHOST + port + "/intention/interaction/confirm/1";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(urlConfirm, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Congratulations, the intent was completed successfully.", response.getBody());
    }

    @Test
    @DirtiesContext
    void confirm_intention_with_non_owner_user_test() throws IOException {
        String urlProceed = HTTP_LOCALHOST + port + "/intention/interaction/proceed/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginTwo));
        var httpEntity = new HttpEntity<>(headers);
        restTemplate.postForEntity(urlProceed, httpEntity, String.class);
        String urlConfirm = HTTP_LOCALHOST + port + "/intention/interaction/confirm/1";
        ResponseEntity<String> response = restTemplate.postForEntity(urlConfirm, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The entered user is not the owner of the intention.", response.getBody());
    }

    @Test
    @DirtiesContext
    void get_actives_intention_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/getActivesIntentions/example@example.com";
        ResponseEntity<IntentionPurchaseSaleUserInfo> response = restTemplate.getForEntity(url, IntentionPurchaseSaleUserInfo.class);
        IntentionPurchaseSaleUserInfo intentionPurchaseSaleUserInfo = response.getBody();
        Assertions.assertNotNull(intentionPurchaseSaleUserInfo);
        Assertions.assertEquals(3, intentionPurchaseSaleUserInfo.getIntentionPurchaseSaleSummarizedList().size());
        Assertions.assertEquals("example@example.com", intentionPurchaseSaleUserInfo.getEmail());
    }

    @Test
    @DirtiesContext
    void get_volume_operated_between_dates_test() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String nowString = now.toString();
        String yesterdayString = now.minusDays(1).toString();
        String url = HTTP_LOCALHOST + port + "/intention/betweenDates/" + yesterdayString+ "/" + nowString;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        ParameterizedTypeReference<IntentionPurchaseSaleVolumeInfo> responseType = new ParameterizedTypeReference<IntentionPurchaseSaleVolumeInfo>() {};
        ResponseEntity<IntentionPurchaseSaleVolumeInfo> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
        IntentionPurchaseSaleVolumeInfo intentionPurchaseSaleVolumeInfo = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(intentionPurchaseSaleVolumeInfo);
        Assertions.assertEquals(2, intentionPurchaseSaleVolumeInfo.getIntentionPurchaseSaleResultList().size());
    }

    @Test
    @DirtiesContext
    void confirm_intention_purchase_with_difference_in_quotation_test() throws IOException {
        String urlProceedOne = HTTP_LOCALHOST + port + "/intention/interaction/proceed/5";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginTwo));
        restTemplate.postForEntity(urlProceedOne, new HttpEntity<>(headers), String.class);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        String urlConfirmOne = HTTP_LOCALHOST + port + "/intention/interaction/confirm/5";
        ResponseEntity<String> responseOne = restTemplate.postForEntity(urlConfirmOne, new HttpEntity<>(headers), String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseOne.getStatusCode());
        Assertions.assertEquals("There is a 5% difference between the quotes. The operation is cancelled.", responseOne.getBody());
    }

    @Test
    @DirtiesContext
    void confirm_intention_sell_with_difference_in_quotation_test() throws IOException {
        String urlProceedOne = HTTP_LOCALHOST + port + "/intention/interaction/proceed/6";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        restTemplate.postForEntity(urlProceedOne, new HttpEntity<>(headers), String.class);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authHelper.getToken(userLoginOne));
        String urlConfirmOne = HTTP_LOCALHOST + port + "/intention/interaction/confirm/6";
        ResponseEntity<String> responseOne = restTemplate.postForEntity(urlConfirmOne, new HttpEntity<>(headers), String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseOne.getStatusCode());
        Assertions.assertEquals("There is a 5% difference between the quotes. The operation is cancelled.", responseOne.getBody());
    }

}
