package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.CryptoActiveVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.TransactionRequestPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.OperationService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionRequestServiceImpl implements TransactionRequestService {

    @Autowired
    private TransactionRequestPersistence transactionRequestPersistence;

    @Autowired
    private OperationService operationService;

    public String register(@Valid TransactionRequest transactionRequest) {
        return String.valueOf(transactionRequestPersistence.save(transactionRequest).getId());
    }

    @Override
    public List<TransactionRequest> getTransactionsByState(TransactionState transactionState) {
        return transactionRequestPersistence.getTransactionsByState(transactionState);
    }

    @Override
    public List<TransactionRequest> findAll() {
        return transactionRequestPersistence.findAll();
    }

    @Override
    public TransactionRequestVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        List<TransactionRequest> transactionRequestList = transactionRequestPersistence.findOperationBetweenDates(email, startDate, endDate, TransactionState.ACCEPTED);
        Map<String, BigDecimal> cryptoQuotationCache = new HashMap<>();
        List<CryptoActiveVolumeInfo> cryptoActivesList = new ArrayList<>();
        BigDecimal pesos = operationService.getPesosValueByDollar();
        BigDecimal totalDollarAmount = BigDecimal.ZERO;
        BigDecimal totalPesosAmount = BigDecimal.ZERO;

        for(TransactionRequest transactionRequest : transactionRequestList) {
            totalDollarAmount = totalDollarAmount.add(transactionRequest.getDollarAmount());
            totalPesosAmount = totalPesosAmount.add(transactionRequest.getPesosAmount());
            CryptoActive actuallyCryptoActive = transactionRequest.getCryptoActive();
            String cryptoCoinName = actuallyCryptoActive.getCryptoCoinName();
            BigDecimal amountOfCryptoCoin = actuallyCryptoActive.getAmountOfCryptoCoin();

            if(!cryptoQuotationCache.containsKey(cryptoCoinName)) {
                cryptoQuotationCache.put(cryptoCoinName, operationService.getCryptoCoinCotizationByName(cryptoCoinName));
            }

            CryptoActiveVolumeInfo cryptoDTO = new CryptoActiveVolumeInfo(
             cryptoCoinName,amountOfCryptoCoin, cryptoQuotationCache.get(cryptoCoinName),
             operationService.getTheValueOfAnAmountOfCryptoCoinInPesos(cryptoCoinName, amountOfCryptoCoin)
            );

            cryptoActivesList.add(cryptoDTO);
        }

        return new TransactionRequestVolumeInfo(LocalDateTime.now(), totalDollarAmount, totalPesosAmount, cryptoActivesList);
    }
}
