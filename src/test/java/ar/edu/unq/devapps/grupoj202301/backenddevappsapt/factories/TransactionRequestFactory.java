package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequestFactory {

    public static TransactionRequest anyTransactionRequest(){
        CryptoActive cryptoActive = CryptoActiveFactory.anyCryptoActive();
        User user = UserFactory.anyUser();
        return new TransactionRequest(cryptoActive,LocalDateTime.now(), user, new BigDecimal("1"), new BigDecimal("10"), new BigDecimal("0.01"), TransactionType.SELL);
    }
}
