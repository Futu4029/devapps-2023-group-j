package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionDataDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRequestService {
    TransactionRequestVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
    String createIntentionPurchaseSale(IntentionPSDTO intention) throws IOException;
    String interactWithATransactionRequest( TransactionDataDTO transactionDataDTO);
    String confirmReception(TransactionDataDTO transactionDataDTO);
    String cancelTransactionRequest(TransactionDataDTO transactionDataDTO);
    List<TransactionRequest> getTransactionsByState(String email, TransactionState transactionState) throws IOException;
    TransactionRequest getTransactionsById(long transactionId);
    TransactionRequestVolumeInfo calculateTransactionRequestVolumeInfo(List<TransactionRequest> transactionRequestList) throws IOException;
    void deleteAll();
}
