package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initialization;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories.CryptoCoinFactory;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        logger.warn("Init Data Using DataBase - Initializing CryptoCoins");
        startInitialization();
    }

    private void startInitialization() throws IOException {
        List<CryptoCoin> cryptoCoinList = new ArrayList<>();
        List<String> cryptoCoinNamesList = List.of("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
                "NEOUSDT", "DOTUSDT", "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT"
        );

        for (String cryptoCoinName : cryptoCoinNamesList) {
            CryptoCoin cryptoCoin = CryptoCoinFactory.anyCryptoCoin();
            cryptoCoin.setName(cryptoCoinName);
            cryptoCoinList.add(cryptoCoin);
        }
        cryptoCoinService.saveAllCryptoCoins(cryptoCoinList);
    }
}