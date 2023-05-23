package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.CryptoActiveVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.TransactionRequestPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.CryptoActiveUnavailableException;
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
    private CryptoCoinService cryptoCoinService;

    @Override
    public List<TransactionRequest> getTransactionsByState(TransactionState transactionState) {
        return transactionRequestPersistence.getTransactionsByState(transactionState);
    }

    @Override
    public TransactionRequestVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        List<TransactionRequest> transactionRequestList = transactionRequestPersistence.findOperationBetweenDates(email, startDate, endDate, TransactionState.ACCEPTED);
        Map<String, BigDecimal> cryptoQuotationCache = new HashMap<>();
        List<CryptoActiveVolumeInfo> cryptoActivesList = new ArrayList<>();
        BigDecimal totalDollarAmount = BigDecimal.ZERO;
        BigDecimal totalPesosAmount = BigDecimal.ZERO;

        for(TransactionRequest transactionRequest : transactionRequestList) {
            totalDollarAmount = totalDollarAmount.add(transactionRequest.getDollarAmount());
            totalPesosAmount = totalPesosAmount.add(transactionRequest.getPesosAmount());
            CryptoActive actuallyCryptoActive = transactionRequest.getCryptoActive();
            String cryptoCoinName = actuallyCryptoActive.getCryptoCoinName();
            BigDecimal amountOfCryptoCoin = actuallyCryptoActive.getAmountOfCryptoCoin();

            if(!cryptoQuotationCache.containsKey(cryptoCoinName)) {
                cryptoQuotationCache.put(cryptoCoinName, cryptoCoinService.getQuotationByName(cryptoCoinName));
            }

            BigDecimal quotation = cryptoQuotationCache.get(cryptoCoinName);

            CryptoActiveVolumeInfo cryptoDTO = new CryptoActiveVolumeInfo(
             cryptoCoinName,amountOfCryptoCoin, quotation, cryptoCoinService.getPesosValueByDollar().multiply(quotation)
            );

            cryptoActivesList.add(cryptoDTO);
        }

        return new TransactionRequestVolumeInfo(LocalDateTime.now(), totalDollarAmount, totalPesosAmount, cryptoActivesList);
    }

    @Override
    public String createIntentionPurchaseSale(User user, CryptoActive cryptoActive, TransactionType transactionType) throws IOException {
        if(TransactionType.SELL == transactionType && !user.getDigitalWallet().getCryptoActiveIfPossibleToSell(cryptoActive.getCryptoCoinName(), cryptoActive.getAmountOfCryptoCoin())) {
            throw new CryptoActiveUnavailableException("The crypto asset you are trying to sell is not in your wallet " +
                    "or the amount entered to sell is greater than that available.");
        }

        BigDecimal dollarAmount = cryptoCoinService.getQuotationByName(cryptoActive.getCryptoCoinName()).multiply(cryptoActive.getAmountOfCryptoCoin());
        BigDecimal pesosAmount = cryptoCoinService.getTheValueOfAnAmountOfCryptoCoinInPesos(cryptoActive.getCryptoCoinName(), cryptoActive.getAmountOfCryptoCoin());
        TransactionRequest transactionRequest = new TransactionRequest(cryptoActive, LocalDateTime.now(), user, dollarAmount, pesosAmount, transactionType);
        return String.valueOf(transactionRequestPersistence.save(transactionRequest).getId());
    }
}
