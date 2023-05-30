package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.Initializer;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
        private CryptoCoinService cryptoCoinService;

        @PostConstruct
        public void initialize() throws IOException {
            String HSQLDB_CLASS_NAME = "org.hsqldb.jdbc.JDBCDriver";

            if (className.equals(HSQLDB_CLASS_NAME)){
                startInitialization();
            }
        }

        private void startInitialization() throws IOException {
            List<CryptoCoin> cryptoCoinList = new ArrayList<>();
            List<String> cryptoCoinNamesList = List.of("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
                    "NEOUSDT", "DOTUSDT", "ETHUSDT", "CAKEUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT"
            );

            for (String cryptoCoinName : cryptoCoinNamesList) {
                registerElement(cryptoCoinName);
            }
        }

        public void registerElement(String cryptoCoinName) throws IOException {
            CryptoCoin cryptoCoin = new CryptoCoin(cryptoCoinName);
            QuotationByDate quotationByDate = new QuotationByDate(cryptoCoinService.getCryptoCoinCotizationByName(cryptoCoinName));
            cryptoCoin.addQuotation(quotationByDate);
            cryptoCoinService.registerElement(cryptoCoin);
        }
}
