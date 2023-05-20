package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CryptoCoinInitializer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driver-class-name:NONE}")
    private String className;

    @Autowired
    private CryptoCoinService cryptoCoinService;

    @PostConstruct
    public void initialize(){
        String h2_CLASS_NAME = "org.h2.Driver";

        if (className.equals(h2_CLASS_NAME)){
            logger.warn("Init Data Using H2 DataBase - Initializing CryptoCoins");
            startInitialization();
        }else{
            logger.warn("No database was set for the initialization");
        }
    }

    private void startInitialization() {
        List<CryptoCoin> cryptoCoinList = List.of(
                new CryptoCoin("ALICEUSDT", "100"),
                new CryptoCoin("MATICUSDT", "100"),
                new CryptoCoin("AXSUSDT", "100"),
                new CryptoCoin("AAVEUSDT", "100"),
                new CryptoCoin("ATOMUSDT", "100"),
                new CryptoCoin("NEOUSDT", "100"),
                new CryptoCoin("DOTUSDT", "100"),
                new CryptoCoin("ETHUSDT", "100"),
                new CryptoCoin("CAKEUSDT", "100"),
                new CryptoCoin("BTCUSDT", "100"),
                new CryptoCoin("BNBUSDT", "100"),
                new CryptoCoin("ADAUSDT", "100"),
                new CryptoCoin("TRXUSDT", "100"),
                new CryptoCoin("AUDIOUSDT", "100"),
                new CryptoCoin("USD", "1"),
                new CryptoCoin("PESOS", "0.0001")
        );
        cryptoCoinService.saveAll(cryptoCoinList);
    }


}
