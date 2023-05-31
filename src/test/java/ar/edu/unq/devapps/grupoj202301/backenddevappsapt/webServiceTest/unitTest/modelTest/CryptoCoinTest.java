package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.unitTest.modelTest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.CryptoCoinFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.QuotationByDateFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

class CryptoCoinTest {
    final CryptoCoin cryptoCoin = CryptoCoinFactory.anyCryptoCoin();

    @Test
    void get_Name_Test() {
        cryptoCoin.setName("ExampleCoinName");
        Assertions.assertEquals("ExampleCoinName", cryptoCoin.getName());
    }

    @Test
    void get_AddQuotation_Test() {
        QuotationByDate quotationByDateOne = QuotationByDateFactory.anyQuotationByDate("11");
        cryptoCoin.addQuotation(quotationByDateOne);
        Assertions.assertEquals(cryptoCoin.getQuotationByDates(), List.of(quotationByDateOne));
    }

    @Test
    void get_CryptoCoin_Id_Test() {
        Assertions.assertEquals("ExampleCoin", cryptoCoin.getId());
    }
}
