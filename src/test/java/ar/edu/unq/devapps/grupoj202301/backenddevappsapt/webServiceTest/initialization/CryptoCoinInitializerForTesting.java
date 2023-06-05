package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.initialization;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.CryptoCoinFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories.QuotationByDateFactory;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
@Profile("test")
public class CryptoCoinInitializerForTesting {

    public CryptoCoinInitializerForTesting(CryptoCoinService cryptoCoinService){
        this.cryptoCoinService = cryptoCoinService;
    }
    protected final Log logger = LogFactory.getLog(getClass());

    private final CryptoCoinService cryptoCoinService;

    @PostConstruct
    public void initialize() throws IOException {
        logger.warn("Init Data Using DataBase Test - Initializing CryptoCoins");
        startInitialization();
    }

    private void startInitialization() throws IOException {
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
