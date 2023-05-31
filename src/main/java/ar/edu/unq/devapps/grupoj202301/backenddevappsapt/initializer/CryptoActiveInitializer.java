package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.CryptoActivePersistence;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Profile({"!test", "dev"})
public class CryptoActiveInitializer {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private CryptoActivePersistence cryptoActivePersistence;

    @PostConstruct
    public void initialize(){
        logger.warn("Init Data Using HSQLDB DataBase - Initializing CryptoActives");
        startInitialization();
    }

    private void startInitialization() {
        List<CryptoActive> list = List.of(
        new CryptoActive(1L, "ALICEUSDT", new BigDecimal("100")),
        new CryptoActive(2L, "MATICUSDT", new BigDecimal("100")),
        new CryptoActive(3L, "AXSUSDT", new BigDecimal("100")),
        new CryptoActive(4L, "AAVEUSDT", new BigDecimal("100")));
        cryptoActivePersistence.saveAll(list);
    }


}
