package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinFactory {
    public static CryptoCoin anyCryptoCoin(){
        return new CryptoCoin("ExampleCoin", new BigDecimal("100.000000"), LocalDateTime.now());
    }
}
