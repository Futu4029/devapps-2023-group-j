package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.webServiceTest.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.cryptoCoin.CryptoCoinDTO;

public class CryptoCoinDTOFactory {
    public static CryptoCoinDTO anyCryptoCoinDTO(){
        return new CryptoCoinDTO("ExampleCoin", QuotationByDateFactory.anyQuotationByDate("100"));
    }
}
