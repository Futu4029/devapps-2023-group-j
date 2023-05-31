package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.unitTest.modelTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.QuotationByDateFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuotationByDateTest {
    final QuotationByDate quotationByDate = QuotationByDateFactory.anyQuotationByDate("22");

    @Test
    void get_Id_Test() {
        quotationByDate.setId(Long.parseLong("2"));
        Assertions.assertEquals(Long.parseLong("2"), quotationByDate.getId());
    }

    @Test
    void get_Date_Test() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 25, 12, 0);
        quotationByDate.setDate(dateTime);
        Assertions.assertEquals(dateTime, quotationByDate.getDate());
    }

    @Test
    void get_Quotation_Test() {
        BigDecimal bigDecimal = new BigDecimal(Long.parseLong("1"));
        quotationByDate.setQuotation(bigDecimal);
        Assertions.assertEquals(bigDecimal, quotationByDate.getQuotation());
    }
}
