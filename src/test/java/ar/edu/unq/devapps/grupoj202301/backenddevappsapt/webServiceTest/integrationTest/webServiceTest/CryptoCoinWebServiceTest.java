package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementNotRegisteredException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.CryptoCoinWebService;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CryptoCoinWebServiceTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private CryptoCoinWebService cryptoCoinWebService;

    @Autowired
    private TestRestTemplate restTemplate;

    private List<String> cryptoCoinNamesList;

    @BeforeEach
    void setUp() {
        cryptoCoinNamesList = List.of("AUDIOUSDT","ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
                "NEOUSDT", "DOTUSDT", "ETHUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT");
    }

    @Test
    @DirtiesContext
    void get_The_Last_24_Hours_Of_Quotation() {
        CryptoCoin cryptoCoin = this.restTemplate.getForObject(HTTP_LOCALHOST + port + "/cryptocoins/getTheLast24HoursOfQuotation/BTCUSDT", CryptoCoin.class);
        Assertions.assertEquals(2, cryptoCoin.getQuotationByDates().size());
    }

    @Test
    @DirtiesContext
    void get_Quotation_List() {
        String url = HTTP_LOCALHOST + port + "/cryptocoins/getCryptoCoinsQuotations";
        ParameterizedTypeReference<List<CryptoCoinDTO>> responseType = new ParameterizedTypeReference<List<CryptoCoinDTO>>() {};
        ResponseEntity<List<CryptoCoinDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        List<CryptoCoinDTO> cryptoCoinList = response.getBody();
        Assertions.assertEquals(13, cryptoCoinList.size());

        for(int i = 0; i < cryptoCoinNamesList.size(); i++) {
            Assertions.assertTrue(cryptoCoinNamesList.contains(cryptoCoinList.get(i).getName()));
        }
    }

    @Test
    @DirtiesContext
    void get_The_Last_24_Hours_Of_Quotation_By_Non_Existent_CryptoCoin() {
        ElementNotRegisteredException exception = Assertions.assertThrows(ElementNotRegisteredException.class, () -> {
            cryptoCoinWebService.getTheLast24HoursOfQuotation("ExampleCoinNonExistent");
        });
        String actualErrorMessage = exception.getMessage();
        Assertions.assertEquals("Error: The element with id ExampleCoinNonExistent is not present", actualErrorMessage);
    }
}
