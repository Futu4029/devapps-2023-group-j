package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;

import java.util.ArrayList;

public class CryptoCoinDTOFactory {
    public static CryptoCoinDTO anyCryptoCoinDTO(){
        return new CryptoCoinDTO("ExampleCoin", new ArrayList<>());
    }
}
