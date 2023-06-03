package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.IntentionPurchaseSaleFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.IntentionPurchaseSaleWebService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntentionPurchaseSaleWebServiceTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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
        Assertions.assertEquals("example", createdIntention.getName());
    }
}
