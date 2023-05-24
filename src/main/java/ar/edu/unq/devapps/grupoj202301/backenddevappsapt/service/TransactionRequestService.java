package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionDataDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRequestService {
    TransactionRequestVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
    String createIntentionPurchaseSale(User user, CryptoActive cryptoActive, TransactionType transactionType) throws IOException;
    List<TransactionRequest> getTransactionsByState(TransactionState transactionState) throws IOException;
    void updateStatus(TransactionRequest transactionRequest, TransactionState transactionState);
    TransactionRequest getTransactionsById(long transactionId);
    void interactWithATransactionRequest(User user, TransactionRequest transactionRequest);
    void cancelIfYouAreTheOwner(User user, TransactionRequest transactionRequest);
    void checkPriceDifference(TransactionDataDTO transactionDataDTO);
}
