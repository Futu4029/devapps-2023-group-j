package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.integrationTest.webServiceTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ElementNotRegisteredException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.CryptoCoinFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.QuotationByDateFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.UserFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.initialization.CryptoCoinInitializerForTesting;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.CryptoCoinWebService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CryptoCoinWebServiceTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private CryptoCoinWebService cryptoCoinWebService;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DirtiesContext
    void get_The_Last_24_Hours_Of_Quotation() {
        this.restTemplate.getRestTemplate();
        CryptoCoinDTO cryptoCoinDTO = this.restTemplate.getForObject(HTTP_LOCALHOST + port + "/cryptocoins/getTheLast24HoursOfQuotation/BTCUSDT", CryptoCoinDTO.class);
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
