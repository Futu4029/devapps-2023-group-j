package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface CryptoCoinService {
    List<CryptoCoin> findAll();
    void saveAll(List<CryptoCoin> list);
    CryptoCoin findByName(String name);
    BigDecimal getTheValueOfAnAmountOfCryptoCoinInPesos(String name, BigDecimal amount) throws IOException;
    BigDecimal getCryptoCoinCotizationByName(String cryptoCoinName) throws IOException;
    BigDecimal getPesosValueByDollar() throws IOException;
}
