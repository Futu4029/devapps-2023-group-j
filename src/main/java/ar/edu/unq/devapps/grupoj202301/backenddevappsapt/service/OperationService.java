package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface OperationService {
    List<CryptoCoin> findAll();
    void saveAll(List<CryptoCoin> list);
    CryptoCoin findByName(String name);
    BigDecimal getTheValueOfAnAmountOfCryptoCoinInPesos(String name, BigDecimal amount) throws IOException;
    BigDecimal getCryptoCoinCotizationByName(String cryptoCoinName) throws IOException;
    BigDecimal getPesosValueByDollar() throws IOException;
    TransactionRequest createIntentionPurchaseSale(User user, CryptoActive cryptoActive, TransactionType transactionType) throws IOException;
}
