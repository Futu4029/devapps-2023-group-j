package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRequestService {
    List<TransactionRequest> findAll();
    TransactionRequestVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate);
    void save(TransactionRequest transactionRequest);
}
