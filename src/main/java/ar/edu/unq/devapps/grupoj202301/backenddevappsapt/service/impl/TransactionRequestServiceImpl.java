package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.CryptoActiveVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoCoin;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.TransactionRequestPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionRequestServiceImpl implements TransactionRequestService {

    @Autowired
    private TransactionRequestPersistence transactionRequestPersistence;

    @Autowired
    private CryptoCoinService cryptoCoinService;

    @Override
    public List<TransactionRequest> findAll() {
        return transactionRequestPersistence.findAll();
    }

    @Override
    public TransactionRequestVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) {
        List<TransactionRequest> transactionRequestList = transactionRequestPersistence.findOperationBetweenDates(email, startDate, endDate, TransactionState.ACCEPTED);
        List<CryptoActiveVolumeInfo> cryptoActivesList = new ArrayList<>();
        CryptoCoin pesos = new CryptoCoin();
        BigDecimal totalDollarAmount = BigDecimal.ZERO;
        BigDecimal totalPesosAmount = BigDecimal.ZERO;
        transactionRequestList.forEach(t -> {
                    totalDollarAmount.add(t.getDollarAmount());
                    totalPesosAmount.add(t.getPesosAmount());
                    CryptoActiveVolumeInfo cryptoDTO = new CryptoActiveVolumeInfo(
                            t.getCryptoActive().getCoin().getName(),
                            t.getCryptoActive().getPrice(),
                            t.getCryptoActive().getCoin().getPrice(),
                            pesos.getPrice()
                    );
                cryptoActivesList.add(cryptoDTO);
                }
        );

        return new TransactionRequestVolumeInfo(LocalDateTime.now(), totalDollarAmount, totalPesosAmount, cryptoActivesList);
    }

    @Override
    public void save(TransactionRequest transactionRequest) {

        transactionRequestPersistence.save(new TransactionRequest(
                transactionRequest.getCryptoActive(),
                transactionRequest.getAmount(),
                LocalDateTime.now(),
                transactionRequest.getUserOwner(),
                transactionRequest.getCryptoActive().getCoin().getPrice().multiply(transactionRequest.getAmount()),
                cryptoCoinService.toPesos(transactionRequest.getCryptoActive().getCoin().getName(), transactionRequest.getAmount()),
                transactionRequest.getTransactionType())
        );
    }
}