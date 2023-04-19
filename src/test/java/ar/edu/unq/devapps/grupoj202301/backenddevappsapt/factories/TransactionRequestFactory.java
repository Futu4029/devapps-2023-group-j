package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.factories;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.DigitalWallet;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionData;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequestFactory {

    public static TransactionRequest anyTransactionRequest(){
        CryptoActive cryptoActive = CryptoActiveFactory.anyCryptoActive();
        TransactionData transactionData = TransactionDataFactory.anyTransactionDataPurchase();
        return new TransactionRequest(1L, cryptoActive,new BigDecimal("50"),LocalDateTime.now(),transactionData);
    }
}
