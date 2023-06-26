package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.initialization;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.CryptoCoinFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.QuotationByDateFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
public class CryptoCoinInitializerForTesting {
    protected final Log logger = LogFactory.getLog(getClass());
    private final CryptoCoinService cryptoCoinService;

    public CryptoCoinInitializerForTesting(CryptoCoinService cryptoCoinService){
        this.cryptoCoinService = cryptoCoinService;
    }

    protected void startInitialization() throws IOException {
        logger.warn("TEST - Initializing CryptoCoins");
        CryptoCoin cryptoCoin = new CryptoCoin("AUDIOUSDT");
        QuotationByDate quotationByDate = new QuotationByDate(cryptoCoinService.getExternalQuotationByName("AUDIOUSDT"));
        cryptoCoin.addQuotation(quotationByDate);
        cryptoCoinService.registerElement(cryptoCoin);

        List<String> cryptoCoinNamesList = List.of("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
                "NEOUSDT", "DOTUSDT", "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT");

        for (String cryptoCoinName : cryptoCoinNamesList) {
            cryptoCoin = CryptoCoinFactory.anyCryptoCoin();
            cryptoCoin.setName(cryptoCoinName);
            QuotationByDate firstQuotation = QuotationByDateFactory.anyQuotationByDate("100");
            QuotationByDate secondQuotation = QuotationByDateFactory.anyQuotationByDate("100");
            QuotationByDate thirdQuotation = QuotationByDateFactory.anyQuotationByDate("100");
            secondQuotation.setDate(LocalDateTime.now().minusHours(10L));
            thirdQuotation.setDate(LocalDateTime.now().minusDays(10L));
            cryptoCoin.addQuotation(firstQuotation);
            cryptoCoin.addQuotation(secondQuotation);
            cryptoCoin.addQuotation(thirdQuotation);
            cryptoCoinService.registerElement(cryptoCoin);
        }
    }
}
