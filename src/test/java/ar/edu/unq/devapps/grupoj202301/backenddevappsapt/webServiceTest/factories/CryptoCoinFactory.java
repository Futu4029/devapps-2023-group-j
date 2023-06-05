package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;

public class CryptoCoinFactory {
    public static CryptoCoin anyCryptoCoin(){
        return new CryptoCoin("ExampleCoin");
    }
}
