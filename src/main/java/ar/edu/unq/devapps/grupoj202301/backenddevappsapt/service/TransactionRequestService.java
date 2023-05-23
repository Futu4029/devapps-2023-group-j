package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRequestService {
    List<TransactionRequest> findAll();
    TransactionRequestVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
    String register(TransactionRequest transactionRequest);
    List<TransactionRequest> getTransactionsByState(TransactionState transactionState) throws IOException;
}
