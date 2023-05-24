package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoActivePersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.TransactionRequestPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoActiveService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransactionRequestInitializer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driver-class-name:NONE}")
    private String className;

    @Autowired
    private TransactionRequestPersistence transactionRequestPersistence;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    @Autowired
    private CryptoActivePersistence cryptoActivePersistence;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize() throws IOException {
        String HSQLDB_CLASS_NAME = "org.hsqldb.jdbc.JDBCDriver";

        if (className.equals(HSQLDB_CLASS_NAME)){
            logger.warn("Init Data Using HSQLDB DataBase - Initializing Transactions");
            startInitialization();
        }else{
            logger.warn("No database was set for the initialization");
        }
    }

    private void startInitialization() throws IOException {
        List<CryptoActive> cryptoActiveList = cryptoActivePersistence.findAll();
        User user = new User("user0@example.com", "00000000", "Name", "Surname", "Address123", "passworD1!", "0123456789012345678900");
        userService.register(user);
        Map<String, BigDecimal> cryptoQuotationCache = new HashMap<>();

        for (CryptoActive crypto : cryptoActiveList){
            if(!cryptoQuotationCache.containsKey(crypto.getCryptoCoinName())) {
                cryptoQuotationCache.put(crypto.getCryptoCoinName(), cryptoCoinService.getQuotationByName(crypto.getCryptoCoinName()));
            }
        }
        List<TransactionRequest> transactionRequestList = List.of(
                new TransactionRequest(cryptoActiveList.get(0), LocalDateTime.now(), user, cryptoQuotationCache.get(cryptoActiveList.get(0).getCryptoCoinName()), cryptoQuotationCache.get(cryptoActiveList.get(0).getCryptoCoinName()).multiply(cryptoActiveList.get(0).getAmountOfCryptoCoin()), cryptoQuotationCache.get(cryptoActiveList.get(0).getCryptoCoinName()).multiply(cryptoActiveList.get(0).getAmountOfCryptoCoin()).multiply(cryptoCoinService.getPesosValueByDollar()), TransactionType.SELL),
                new TransactionRequest(cryptoActiveList.get(1), LocalDateTime.now(), user, cryptoQuotationCache.get(cryptoActiveList.get(1).getCryptoCoinName()), cryptoQuotationCache.get(cryptoActiveList.get(1).getCryptoCoinName()).multiply(cryptoActiveList.get(1).getAmountOfCryptoCoin()), cryptoQuotationCache.get(cryptoActiveList.get(1).getCryptoCoinName()).multiply(cryptoActiveList.get(1).getAmountOfCryptoCoin()).multiply(cryptoCoinService.getPesosValueByDollar()), TransactionType.SELL),
                new TransactionRequest(cryptoActiveList.get(2), LocalDateTime.now(), user, cryptoQuotationCache.get(cryptoActiveList.get(2).getCryptoCoinName()), cryptoQuotationCache.get(cryptoActiveList.get(2).getCryptoCoinName()).multiply(cryptoActiveList.get(2).getAmountOfCryptoCoin()), cryptoQuotationCache.get(cryptoActiveList.get(2).getCryptoCoinName()).multiply(cryptoActiveList.get(2).getAmountOfCryptoCoin()).multiply(cryptoCoinService.getPesosValueByDollar()), TransactionType.SELL),
                new TransactionRequest(cryptoActiveList.get(3), LocalDateTime.now(), user, cryptoQuotationCache.get(cryptoActiveList.get(3).getCryptoCoinName()), cryptoQuotationCache.get(cryptoActiveList.get(3).getCryptoCoinName()).multiply(cryptoActiveList.get(3).getAmountOfCryptoCoin()), cryptoQuotationCache.get(cryptoActiveList.get(3).getCryptoCoinName()).multiply(cryptoActiveList.get(3).getAmountOfCryptoCoin()).multiply(cryptoCoinService.getPesosValueByDollar()), TransactionType.SELL)
        );
        transactionRequestPersistence.saveAll(transactionRequestList);
    }


}
