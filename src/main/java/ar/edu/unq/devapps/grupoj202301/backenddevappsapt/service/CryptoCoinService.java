package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.crypto.CryptoCoinDTO;

import java.io.IOException;
import java.math.BigDecimal;

public interface CryptoCoinService extends GenericService<CryptoCoin> {
    CryptoCoinDTO findCryptoCoinWithQuotationByDatesWithin24Hours(String cryptoCoinName);
    BigDecimal getCryptoCoinCotizationByName(String cryptoCoinName) throws IOException;
}
