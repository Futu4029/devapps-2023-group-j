package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;

import java.io.IOException;
import java.math.BigDecimal;

public interface CryptoCoinService extends GenericService<CryptoCoin> {
    CryptoCoinDTO findCryptoCoinWithQuotationByDatesWithin24Hours(String cryptoCoinName);
    BigDecimal getExternalQuotationByName(String cryptoCoinName) throws IOException;
    BigDecimal getThePriceOfThePurchaseDollar() throws IOException;
    BigDecimal getThePriceOfTheSellDollar() throws IOException;
}
