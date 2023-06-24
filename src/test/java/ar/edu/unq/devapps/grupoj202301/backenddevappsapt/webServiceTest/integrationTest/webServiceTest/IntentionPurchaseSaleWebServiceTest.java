package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleUserInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.IntentionPurchaseSaleFactory;
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

public class IntentionPurchaseSaleWebServiceTest extends AuthHelper{

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() throws IOException {
        registerUser();
    }

    @Test
    @DirtiesContext
    void create_intention() throws IOException {
        String url = HTTP_LOCALHOST + port+ "/intention/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
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
        headers.setBearerAuth(getToken());
        IntentionPurchaseSale anyIntention = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        anyIntention.setCryptoCoinName("ExampleCoin");
        HttpEntity<IntentionPurchaseSale> requestEntity = new HttpEntity<>(anyIntention, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id ExampleCoin is not present", response.getBody());
    }

    @Test
    @DirtiesContext
    void create_intention_with_a_non_existent_user_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        IntentionPurchaseSale anyIntention = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        anyIntention.setEmail("example2@example.com");
        HttpEntity<IntentionPurchaseSale> requestEntity = new HttpEntity<>(anyIntention, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id example2@example.com is not present", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_user_is_owner_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/1/example@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String message =  response.getBody();
        assertEquals("The operation was successfully canceled. You have lost 20 points.", message);
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_user_is_not_bound_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/1/anotherEmail@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The user entered is not related to this intention.", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_no_longer_active_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/4/example@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: Intent is not active.", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_intention_unregistered_user_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/1/example2@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id example2@example.com is not present", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_unregistered_intention_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/18/example@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id 18 is not present", response.getBody());
    }

    @Test
    @DirtiesContext
    void proceed_intention_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/proceed/1/anotherEmail@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Congratulations, the interaction with the intention was successful. " +
                "Wait for the other user to confirm.", response.getBody());
    }

    @Test
    @DirtiesContext
    void proceed_intention_user_owner_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/proceed/1/example@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: You cannot proceed on your own intention.", response.getBody());
    }

    @Test
    @DirtiesContext
    void proceed_intention_when_no_longer_active_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/proceed/3/anotherEmail@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: Intent is not active.", response.getBody());
    }

    @Test
    @DirtiesContext
    void confirm_intention_test() throws IOException {
        String urlProceed = HTTP_LOCALHOST + port + "/intention/interaction/proceed/1/anotherEmail@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        restTemplate.postForEntity(urlProceed, httpEntity, String.class);
        String urlConfirm = HTTP_LOCALHOST + port + "/intention/interaction/confirm/1/example@example.com";
        ResponseEntity<String> response = restTemplate.postForEntity(urlConfirm, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Congratulations, the intent was completed successfully.", response.getBody());
    }

    @Test
    @DirtiesContext
    void confirm_intention_with_non_owner_user_test() throws IOException {
        String urlProceed = HTTP_LOCALHOST + port + "/intention/interaction/proceed/1/anotherEmail@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        restTemplate.postForEntity(urlProceed, httpEntity, String.class);
        String urlConfirm = HTTP_LOCALHOST + port + "/intention/interaction/confirm/1/anotherEmail@example.com";
        ResponseEntity<String> response = restTemplate.postForEntity(urlConfirm, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The entered user is not the owner of the intention.", response.getBody());
    }

    @Test
    @DirtiesContext
    void confirm_intention_purchase_with_difference_in_quotation_test() throws IOException {
        String urlProceedOne = HTTP_LOCALHOST + port + "/intention/interaction/proceed/5/anotherEmail@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        restTemplate.postForEntity(urlProceedOne, httpEntity, String.class);
        String urlConfirmOne = HTTP_LOCALHOST + port + "/intention/interaction/confirm/5/example@example.com";
        ResponseEntity<String> responseOne = restTemplate.postForEntity(urlConfirmOne, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseOne.getStatusCode());
        Assertions.assertEquals("There is a 5% difference between the quotes. The operation is cancelled.", responseOne.getBody());
    }
    @Test
    @DirtiesContext
    void confirm_intention_sell_with_difference_in_quotation_test() throws IOException {
        String urlProceedOne = HTTP_LOCALHOST + port + "/intention/interaction/proceed/6/anotherEmail@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        restTemplate.postForEntity(urlProceedOne, httpEntity, String.class);
        String urlConfirmOne = HTTP_LOCALHOST + port + "/intention/interaction/confirm/6/example@example.com";
        ResponseEntity<String> responseOne = restTemplate.postForEntity(urlConfirmOne, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseOne.getStatusCode());
        Assertions.assertEquals("There is a 5% difference between the quotes. The operation is cancelled.", responseOne.getBody());
    }

    @Test
    @DirtiesContext
    void get_actives_intention_test() throws IOException {
        String url = HTTP_LOCALHOST + port + "/intention/getActivesIntentions/example@example.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<IntentionPurchaseSaleUserInfo> responseType = new ParameterizedTypeReference<IntentionPurchaseSaleUserInfo>() {};
        ResponseEntity<IntentionPurchaseSaleUserInfo> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
        IntentionPurchaseSaleUserInfo intentionPurchaseSaleUserInfo = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
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
        String url = HTTP_LOCALHOST + port + "/intention/betweenDates/example@example.com/" + yesterdayString+ "/" + nowString;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());
        var httpEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<IntentionPurchaseSaleVolumeInfo> responseType = new ParameterizedTypeReference<IntentionPurchaseSaleVolumeInfo>() {};
        ResponseEntity<IntentionPurchaseSaleVolumeInfo> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
        IntentionPurchaseSaleVolumeInfo intentionPurchaseSaleVolumeInfo = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(intentionPurchaseSaleVolumeInfo);
        Assertions.assertEquals(2, intentionPurchaseSaleVolumeInfo.getIntentionPurchaseSaleResultList().size());
    }

}
