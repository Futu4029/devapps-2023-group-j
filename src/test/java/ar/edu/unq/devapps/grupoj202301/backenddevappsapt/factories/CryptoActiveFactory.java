package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import java.math.BigDecimal;

public class CryptoActiveFactory {
    public static CryptoActive anyCryptoActive(){
        CryptoCoin cryptoCoin = CryptoCoinFactory.anyCryptoCoin();
        return new CryptoActive(1L, cryptoCoin.getName(), new BigDecimal("10"));
    }
}
