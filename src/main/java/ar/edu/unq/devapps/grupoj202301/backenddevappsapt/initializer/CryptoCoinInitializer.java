package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CryptoCoinInitializer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private CryptoCoinService cryptoCoinService;

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
            cryptoCoinList.add(new CryptoCoin(cryptoCoinName, cryptoCoinService.getCryptoCoinCotizationByName(cryptoCoinName)));
        }
        cryptoCoinService.saveAllCryptoCoins(cryptoCoinList);
    }
}
