package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuotationByDateFactory {
    public static QuotationByDate anyQuotationByDate(String quotation){
        LocalDateTime dateTime = LocalDateTime.now();
        QuotationByDate quotationByDate = new QuotationByDate(new BigDecimal(quotation));
        quotationByDate.setDate(dateTime);
        return quotationByDate;
    }

}
