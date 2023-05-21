package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.OperationService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CryptoCoinInitializer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driver-class-name:NONE}")
    private String className;

    @Autowired
    private OperationService operationService;

    @PostConstruct
    public void initialize() throws IOException {
        String h2_CLASS_NAME = "org.h2.Driver";

        if (className.equals(h2_CLASS_NAME)){
            logger.warn("Init Data Using H2 DataBase - Initializing CryptoCoins");
            startInitialization();
        }else{
            logger.warn("No database was set for the initialization");
        }
    }

    private void startInitialization() throws IOException {
        List<CryptoCoin> cryptoCoinList = new ArrayList<>();
        List<String> cryptoCoinNamesList = List.of("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
                "NEOUSDT", "DOTUSDT", "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT"
        );

        for (String cryptoCoinName : cryptoCoinNamesList) {
            cryptoCoinList.add(new CryptoCoin(cryptoCoinName, operationService.getCryptoCoinCotizationByName(cryptoCoinName)));
        }
        operationService.saveAll(cryptoCoinList);
    }
}
