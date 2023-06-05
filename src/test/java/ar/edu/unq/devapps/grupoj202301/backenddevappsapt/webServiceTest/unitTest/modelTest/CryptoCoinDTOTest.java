package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.unitTest.modelTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.CryptoCoinDTOFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.QuotationByDateFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class CryptoCoinDTOTest {
    final CryptoCoinDTO cryptoCoinDTO = CryptoCoinDTOFactory.anyCryptoCoinDTO();

    @Test
    void get_Name_Test() {
        cryptoCoinDTO.setName("ExampleCoinName");
        Assertions.assertEquals("ExampleCoinName", cryptoCoinDTO.getName());
    }

    @Test
    void get_QuotationByDates_Test() {
        QuotationByDate quotationByDate = QuotationByDateFactory.anyQuotationByDate("11");
        cryptoCoinDTO.setQuotationByDate(quotationByDate);
        Assertions.assertEquals(quotationByDate, cryptoCoinDTO.getQuotationByDate());
    }
}
