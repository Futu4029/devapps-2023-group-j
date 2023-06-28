package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.initializer;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.QuotationByDate;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.ExternalAPIException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Profile("prod")
public class CryptoCoinInitializer {

    protected final Log logger = LogFactory.getLog(getClass());
    List<String> cryptoCoinNamesList = List.of("ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
            "NEOUSDT", "DOTUSDT", "ETHUSDT", "BTCUSDT", "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT");


        @Autowired
        private CryptoCoinService cryptoCoinService;

        @PostConstruct
        public void initialize() throws IOException {
                startInitialization();
        }

        private void startInitialization() throws IOException {
            for (String cryptoCoinName : cryptoCoinNamesList) {
                registerCrypto(cryptoCoinName);
            }
        }

        public void registerCrypto(String cryptoCoinName) throws IOException {
            CryptoCoin cryptoCoin = new CryptoCoin(cryptoCoinName);
            QuotationByDate quotationByDate = new QuotationByDate(cryptoCoinService.getExternalQuotationByName(cryptoCoinName));
            cryptoCoin.addQuotation(quotationByDate);
            cryptoCoinService.registerElement(cryptoCoin);
        }

        @Scheduled(initialDelay = 600000, fixedRate = 600000)
        public void updateCrypto() throws IOException {
            for (String cryptoCoinName : cryptoCoinNamesList) {
                Optional<CryptoCoin> cryptoCoinOptional = cryptoCoinService.findElementById(cryptoCoinName);
                if(cryptoCoinOptional.isPresent()){
                    CryptoCoin cryptoCoin = cryptoCoinOptional.get();
                    QuotationByDate quotationByDate = new QuotationByDate(cryptoCoinService.getExternalQuotationByName(cryptoCoinName));
                    cryptoCoin.addQuotation(quotationByDate);
                    cryptoCoinService.updateElement(cryptoCoin);
                }else{
                    throw new ExternalAPIException("Could not obtain Binance resource");
                }
            }
            logger.info("Updating quotation from binance");
        }



}
