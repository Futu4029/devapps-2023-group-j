package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;

import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.CryptoActiveVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.IntentionPSDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionDataDTO;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.dto.TransactionRequestVolumeInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.CryptoActive;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.TransactionRequest;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.ActionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionState;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.enum_model.TransactionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.TransactionRequestPersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoActiveService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.TransactionRequestService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.UserService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.validation.exception.PriceDifferenceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionRequestServiceImpl implements TransactionRequestService {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private CryptoActiveService cryptoActiveService;

    @Autowired
    private TransactionRequestPersistence transactionRequestPersistence;

    @Autowired
    private UserService userService;

    @Autowired
    private CryptoCoinService cryptoCoinService;

    @Override
    public List<TransactionRequest> getTransactionsByState(String email, TransactionState transactionState) {
        return transactionRequestPersistence.getTransactionsByState(email, transactionState);
    }
    public String updateStatus(TransactionRequest transactionRequest, TransactionState transactionState) {
        transactionRequest.setTransactionState(transactionState);
        var response = transactionRequestPersistence.save(transactionRequest).getTransactionState().name();
        logger.info("Transaction request with ID: " + transactionRequest.getId() + " updated. New Transaction State: "+transactionState.name() );
        return response;
    }
    @Override
    public TransactionRequest getTransactionsById(long transactionId) {
        return transactionRequestPersistence.findById(transactionId).orElseThrow();
    }
    @Override
    public TransactionRequestVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        List<TransactionRequest> transactionRequestList = transactionRequestPersistence.findOperationBetweenDates(email, startDate, endDate, TransactionState.ACCEPTED);
        return calculateTransactionRequestVolumeInfo(transactionRequestList);
    }

    public TransactionRequestVolumeInfo calculateTransactionRequestVolumeInfo(List<TransactionRequest> transactionRequestList) throws IOException {
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
    public void deleteAll() {
        transactionRequestPersistence.deleteAllInBatch();
    }

    @Override
    public String createIntentionPurchaseSale(IntentionPSDTO intention) throws IOException {
        User user = userService.getUserByEmail(intention.getUserEmail());
        CryptoActive cryptoActive = cryptoActiveService.save(new CryptoActive(intention.getCryptoCoinName(), intention.getAmountOfCryptoCoin()));
        BigDecimal quotation = cryptoCoinService.getQuotationByName(cryptoActive.getCryptoCoinName());
        BigDecimal dollarAmount = quotation.multiply(cryptoActive.getAmountOfCryptoCoin());
        BigDecimal pesosAmount = cryptoCoinService.getTheValueOfAnAmountOfCryptoCoinInPesos(cryptoActive.getCryptoCoinName(), cryptoActive.getAmountOfCryptoCoin());
        TransactionRequest transactionRequest = new TransactionRequest(cryptoActive, LocalDateTime.now(), user, quotation, dollarAmount, pesosAmount, intention.getTransactionType());
        var response = String.valueOf(transactionRequestPersistence.save(transactionRequest).getId());
        logger.info("New transaction request created with ID: " + response + " for user "+user.getEmail());
        return response;
    }
    @Override
    public String interactWithATransactionRequest( TransactionDataDTO transactionDataDTO) {
        ArrayList<Object> result = getInformationFrom(transactionDataDTO);
        User user = (User) result.get(0);
        TransactionRequest transactionRequest = (TransactionRequest) result.get(1);
        transactionRequest.setUserSecondary(user);
        if(transactionRequest.getTransactionType() == TransactionType.PURCHASE) {
            transactionRequest.setActionType(ActionType.MAKETHETRANSFER);
        } else {
            transactionRequest.setActionType(ActionType.CONFIRMRECEPTION);
        }
        var response = transactionRequestPersistence.save(transactionRequest).getActionType().name();
        logger.info("Transaction request interaction with Id: " + transactionRequest.getId() + " for user "+user.getEmail()+". Update action state: "+ response);
        return response;
    }

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

    @Override
    public String confirmReception(TransactionDataDTO transactionDataDTO) {
        ArrayList<Object> result = getInformationFrom(transactionDataDTO);
        User user = (User) result.get(0);
        TransactionRequest transactionRequest = (TransactionRequest) result.get(1);
        checkPriceDifference(transactionDataDTO);
        userService.confirmReception(user, transactionRequest);
        return updateStatus(transactionRequest, TransactionState.ACCEPTED);
    }

    @Override
    public String cancelTransactionRequest(TransactionDataDTO transactionDataDTO) {
        ArrayList<Object> result = getInformationFrom(transactionDataDTO);
        User user = (User) result.get(0);
        TransactionRequest transactionRequest = (TransactionRequest) result.get(1);
        userService.discountReputation(user, transactionRequest);
        if(user.getEmail().equals(transactionRequest.getUserOwner().getEmail())) {
            transactionRequest.setTransactionState(TransactionState.CANCELLED);
            logger.info("Transaction request cancelled with ID: " + transactionRequest.getId() + " for owneruser "+user.getEmail());
        }
        if(user.getEmail().equals(transactionRequest.getUserSecondary().getEmail())) {
            transactionRequest.setActionType(null);
            transactionRequest.setUserSecondary(null);
            logger.info("Transaction request cancelled with ID: " + transactionRequest.getId() + " by user "+user.getEmail());
        }
        return transactionRequestPersistence.save(transactionRequest).getTransactionState().name();
    }

    private boolean isADifferenceOf5BetweenQuotations(BigDecimal quotationA, BigDecimal quotationB) {
        BigDecimal differencePercentage = quotationA.subtract(quotationB)
                                          .divide(quotationB, MathContext.DECIMAL128)
                                          .multiply(BigDecimal.valueOf(100));

        BigDecimal fivePercent = BigDecimal.valueOf(5);
        int comparisonResult = differencePercentage.compareTo(fivePercent);

        return comparisonResult >= 0;
    }

    public ArrayList<Object> getInformationFrom(TransactionDataDTO transactionDataDTO) {
        User user = userService.getUserByEmail(transactionDataDTO.getEmail());
        TransactionRequest transactionRequest = getTransactionsById(transactionDataDTO.getTransactionId());
        ArrayList<Object> result = new ArrayList<>();
        result.add(user);
        result.add(transactionRequest);
        return  result;
    }
}
