package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.CryptoActiveVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionDataDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.ActionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.TransactionRequestPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.CryptoActiveUnavailableException;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.PriceDifferenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Long.parseLong;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionRequestServiceImpl implements TransactionRequestService {

    @Autowired
    private TransactionRequestPersistence transactionRequestPersistence;

    @Autowired
    private CryptoCoinService cryptoCoinService;

    public List<TransactionRequest> getTransactionsByState(TransactionState transactionState) {
        return transactionRequestPersistence.getTransactionsByState(transactionState);
    }
    public void updateStatus(TransactionRequest transactionRequest, TransactionState transactionState) {
        transactionRequest.setTransactionState(transactionState);
        transactionRequestPersistence.save(transactionRequest);
    }
    public TransactionRequest getTransactionsById(long transactionId) {
        return transactionRequestPersistence.findById(transactionId).orElseThrow();
    }
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
    public String createIntentionPurchaseSale(User user, CryptoActive cryptoActive, TransactionType transactionType) throws IOException {
        if(TransactionType.SELL == transactionType && !user.getDigitalWallet().getCryptoActiveIfPossibleToSell(cryptoActive.getCryptoCoinName(), cryptoActive.getAmountOfCryptoCoin())) {
            throw new CryptoActiveUnavailableException("The crypto asset you are trying to sell is not in your wallet " +
                    "or the amount entered to sell is greater than that available.");
        }

        BigDecimal quotation = cryptoCoinService.getQuotationByName(cryptoActive.getCryptoCoinName());
        BigDecimal dollarAmount = quotation.multiply(cryptoActive.getAmountOfCryptoCoin());
        BigDecimal pesosAmount = cryptoCoinService.getTheValueOfAnAmountOfCryptoCoinInPesos(cryptoActive.getCryptoCoinName(), cryptoActive.getAmountOfCryptoCoin());
        TransactionRequest transactionRequest = new TransactionRequest(cryptoActive, LocalDateTime.now(), user, quotation, dollarAmount, pesosAmount, transactionType);
        return String.valueOf(transactionRequestPersistence.save(transactionRequest).getId());
    }
    public void interactWithATransactionRequest(User user, TransactionRequest transactionRequest) {
        User userOwner = transactionRequest.getUserOwner();
        transactionRequest.setUserSecondary(user);

        if(transactionRequest.getTransactionType() == TransactionType.PURCHASE) {
            transactionRequest.setActionType(ActionType.MAKETHETRANSFER);
        } else {
            transactionRequest.setActionType(ActionType.CONFIRMRECEPTION);
        }
        transactionRequestPersistence.save(transactionRequest);
    }

    @Override
    public void cancelIfYouAreTheOwner(User user, TransactionRequest transactionRequest) {
        if(user.getEmail().equals(transactionRequest.getUserOwner().getEmail())) {
            transactionRequest.setTransactionState(TransactionState.CANCELLED);
        }
        if(user.getEmail().equals(transactionRequest.getUserSecondary().getEmail())) {
            transactionRequest.setActionType(null);
            transactionRequest.setUserSecondary(null);
        }
        transactionRequestPersistence.save(transactionRequest);
    }

    @Override
    public void checkPriceDifference(TransactionDataDTO transactionDataDTO) {
       TransactionRequest transactionRequest = transactionRequestPersistence.getReferenceById(transactionDataDTO.getTransactionId());
       BigDecimal originalQuotation = transactionRequest.getQuotation();
       BigDecimal currentQuotation = cryptoCoinService.getQuotationByName(transactionRequest.getCryptoActive().getCryptoCoinName());

       if((transactionRequest.getTransactionType().equals(TransactionType.PURCHASE) &&
               this.isADifferenceOf5BetweenQuotations(currentQuotation, originalQuotation)) ||
               (transactionRequest.getTransactionType().equals(TransactionType.SELL) &&
               this.isADifferenceOf5BetweenQuotations(originalQuotation, currentQuotation))
       ) {
           transactionRequest.setTransactionState(TransactionState.CANCELLED);
           transactionRequest.setActionType(ActionType.CANCEL);
           transactionRequestPersistence.save(transactionRequest);
           throw new PriceDifferenceException("There is a 5% difference between the quotes. The operation is cancelled.");
       }
    }


    private boolean isADifferenceOf5BetweenQuotations(BigDecimal quotationA, BigDecimal quotationB) {
        BigDecimal differencePercentage = quotationA.subtract(quotationB)
                                          .divide(quotationB, MathContext.DECIMAL128)
                                          .multiply(BigDecimal.valueOf(100));

        BigDecimal fivePercent = BigDecimal.valueOf(5);
        int comparisonResult = differencePercentage.compareTo(fivePercent);

        return comparisonResult >= 0;
    }
}
