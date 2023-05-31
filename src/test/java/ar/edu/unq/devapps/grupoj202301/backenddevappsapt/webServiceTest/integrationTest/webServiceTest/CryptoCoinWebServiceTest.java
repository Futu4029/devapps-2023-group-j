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
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webservice.CryptoCoinWebService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CryptoCoinWebServiceTest {
    @Autowired
    CryptoCoinWebService cryptoCoinWebService;

    @Autowired
    CryptoCoinService cryptoCoinService;

    private CryptoCoin cryptoCoin;
    private LocalDateTime localDateTime;
    private QuotationByDate quotationByDateOne;
    private QuotationByDate quotationByDateTwo;
    private QuotationByDate quotationByDateThree;
    private List<QuotationByDate> quotationByDateList;

    @BeforeEach
    void setUp() {
        cryptoCoin = CryptoCoinFactory.anyCryptoCoin();
        quotationByDateOne = QuotationByDateFactory.anyQuotationByDate("22");
        quotationByDateTwo = QuotationByDateFactory.anyQuotationByDate("4");;
        quotationByDateThree = QuotationByDateFactory.anyQuotationByDate("18");;
        quotationByDateTwo.setDate(LocalDateTime.of(2023, Month.MAY, 25, 0, 0));
        cryptoCoin.addQuotation(quotationByDateOne);
        cryptoCoin.addQuotation(quotationByDateTwo);
        cryptoCoin.addQuotation(quotationByDateThree);
        String cryptoCoinId = cryptoCoinService.registerElement(cryptoCoin);
        cryptoCoin = cryptoCoinService.findElementById(cryptoCoinId).get();
        quotationByDateList = cryptoCoin.getQuotationByDates();
    }

    @Test
    @DirtiesContext
    void get_The_Last_24_Hours_Of_Quotation() {
        ResponseEntity<CryptoCoinDTO> result = cryptoCoinWebService.getTheLast24HoursOfQuotation("ExampleCoin");
        List<QuotationByDate> resultList = Objects.requireNonNull(result.getBody()).getQuotationByDates();
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(2, resultList.size());
        Assertions.assertEquals(quotationByDateList.get(0).getId(), resultList.get(0).getId());
        Assertions.assertEquals(quotationByDateList.get(2).getId(), resultList.get(1).getId());
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
