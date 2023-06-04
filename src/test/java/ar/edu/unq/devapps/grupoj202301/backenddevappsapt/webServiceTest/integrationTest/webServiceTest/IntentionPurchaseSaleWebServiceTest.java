package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementNotRegisteredException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.IntentionPurchaseSaleFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.IntentionPurchaseSaleWebService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntentionPurchaseSaleWebServiceTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DirtiesContext
    void create_intention() {
        String url = HTTP_LOCALHOST + port+ "/intention/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        IntentionPurchaseSale anyIntention = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        HttpEntity<IntentionPurchaseSale> requestEntity = new HttpEntity<>(anyIntention, headers);
        ResponseEntity<IntentionPurchaseSale> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, IntentionPurchaseSale.class);
        IntentionPurchaseSale createdIntention = response.getBody();
        assertEquals("example", createdIntention.getName());
    }

    @Test
    @DirtiesContext
    void create_intention_with_a_non_existent_crypto_coin_test() {
        String url = HTTP_LOCALHOST + port + "/intention/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        IntentionPurchaseSale anyIntention = IntentionPurchaseSaleFactory.anyIntentionPurchaseSale();
        anyIntention.setCryptoCoinName("ExampleCoin");
        HttpEntity<IntentionPurchaseSale> requestEntity = new HttpEntity<>(anyIntention, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id ExampleCoin is not present", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_user_is_owner_test() {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/1/example@example.com";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        String message =  response.getBody();
        assertEquals(-20, userService.findElementById("example@example.com").get().getPointsObtained());
        assertEquals("The operation was successfully canceled. You have lost 20 points.", message);
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_user_is_not_bound_test() {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/1/anotherEmail@example.com";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The user entered is not related to this intention.", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_intention_when_no_longer_active_test() {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/4/example@example.com";
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: Intent is not active.", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_intention_unregistered_user_test() {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/1/example2@example.com";
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id example2@example.com is not present", response.getBody());
    }

    @Test
    @DirtiesContext
    void cancel_unregistered_intention_test() {
        String url = HTTP_LOCALHOST + port + "/intention/interaction/cancel/18/example@example.com";
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Error: The element with id 18 is not present", response.getBody());
    }
}
