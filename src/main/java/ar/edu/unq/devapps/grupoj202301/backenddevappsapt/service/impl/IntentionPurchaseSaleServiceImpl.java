 package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.impl;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.*;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.IntentionType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.flags.StatusType;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.User;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.persistence.IntentionPurchaseSalePersistence;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.CryptoCoinService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.GenericService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service.IntentionPurchaseSaleService;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.utilities.validation.exception.UserException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

 @Service
@RequiredArgsConstructor
@Transactional
public class IntentionPurchaseSaleServiceImpl implements IntentionPurchaseSaleService {

    @Autowired
    private IntentionPurchaseSalePersistence intentionPurchaseSalePersistence;

    @Autowired
    private GenericService<User> userService;

    @Autowired
    private CryptoCoinService cryptoCoinService;

    @Override
    public boolean elementIsPresent(IntentionPurchaseSale intentionPurchaseSale) {
        return intentionPurchaseSalePersistence.existsById(intentionPurchaseSale.getId().toString());
    }

    @Override
    public String registerElement(IntentionPurchaseSale intentionPurchaseSale) {
        IntentionPurchaseSale intentionPurchaseSaleResult = intentionPurchaseSalePersistence.save(intentionPurchaseSale);
        return intentionPurchaseSaleResult.getId().toString();
    }

     @Override
     public void updateElement(IntentionPurchaseSale intentionPurchaseSale) {
         if(elementIsPresent(intentionPurchaseSale)) {
             intentionPurchaseSalePersistence.save(intentionPurchaseSale);
         }
     }

     @Override
    public Optional<IntentionPurchaseSale> findElementById(String intentionPurchaseSaleId) {
        return intentionPurchaseSalePersistence.findById(intentionPurchaseSaleId);
    }

    @Override
    public IntentionPurchaseSale create(IntentionPurchaseSaleCoreData intentionPurchaseSaleInitialData) {
        User user = userService.findElementById(intentionPurchaseSaleInitialData.getEmail()).get();
        String cryptoCoinName = intentionPurchaseSaleInitialData.getCryptoCoinName();
        BigDecimal amountOfCryptoCoin = intentionPurchaseSaleInitialData.getAmountOfCryptoCoin();
        BigDecimal quotationBase = intentionPurchaseSaleInitialData.getQuotationBase();
        BigDecimal pesosAmount = quotationBase.multiply(quotationBase);
        IntentionType intentionType = intentionPurchaseSaleInitialData.getIntentionType();
        return intentionPurchaseSalePersistence.save(new IntentionPurchaseSale(user, cryptoCoinName, amountOfCryptoCoin, quotationBase, pesosAmount, intentionType));
    }

     @Override
     public String cancel(String intentionID, String email) {
         IntentionPurchaseSale intentionPurchaseSale = intentionPurchaseSalePersistence.findById(intentionID).get();
         User user = userService.findElementById(email).get();

         if(intentionPurchaseSale.getStatusType().equals(StatusType.ACTIVE)) {
             if(intentionPurchaseSale.getEmail().equals(email)) {
                 intentionPurchaseSale.setStatusType(StatusType.CANCEL);
             } else if (intentionPurchaseSale.getAnotherUserEmail().equals(email)){
                 intentionPurchaseSale.setAnotherUserEmail(null);
             } else {
                 throw new UserException("Error: The user entered is not related to this intention");
             }

             user.discountPoints(20);
             userService.updateElement(user);
             this.updateElement(intentionPurchaseSale);
             return "The operation was successfully canceled. You have lost 20 points.";

         } else  {
             throw new UserException("Error: Intent is not active");
         }
     }

     @Override
    public IntentionPurchaseSaleUserInfo getActivesTransactions(String email) throws IOException {
        User user = userService.findElementById(email).get();
        List<IntentionPurchaseSaleSummarized> intentionPurchaseSaleSummarizedList = new ArrayList<>();
        List<IntentionPurchaseSale> intentionPurchaseSaleList = intentionPurchaseSalePersistence.getActivesTransactions(email, StatusType.ACTIVE);
        BigDecimal purchaseQuotation = cryptoCoinService.getThePriceOfThePurchaseDollar();
        BigDecimal sellQuotation = cryptoCoinService.getThePriceOfTheSellDollar();

        for (IntentionPurchaseSale intentionPurchaseSale : intentionPurchaseSaleList) {
            String cryptoCoinName = intentionPurchaseSale.getCryptoCoinName();
            BigDecimal amountOfCryptoCoin = intentionPurchaseSale.getAmountOfCryptoCoin();
            BigDecimal quotationByIntentionType = intentionPurchaseSale.getQuotationBase();
            BigDecimal pesosAmountByIntentionType = intentionPurchaseSale.getPesosAmount();
            IntentionType intentionType = intentionPurchaseSale.getIntentionType();

            if(intentionPurchaseSale.getIntentionType() == IntentionType.PURCHASE) {
                quotationByIntentionType = quotationByIntentionType.multiply(purchaseQuotation);
                pesosAmountByIntentionType = pesosAmountByIntentionType.multiply(purchaseQuotation);
            } else {
                quotationByIntentionType = quotationByIntentionType.multiply(sellQuotation);
                pesosAmountByIntentionType = pesosAmountByIntentionType.multiply(sellQuotation);

            }
            IntentionPurchaseSaleSummarized intentionPurchaseSaleSummarized = new IntentionPurchaseSaleSummarized(null, cryptoCoinName, amountOfCryptoCoin, quotationByIntentionType, pesosAmountByIntentionType, intentionType);
            intentionPurchaseSaleSummarizedList.add(intentionPurchaseSaleSummarized);
        }
        return new IntentionPurchaseSaleUserInfo(user.getName(), user.getSurname(), user.getEmail(), user.getPointsObtained(), user.getOperationsPerformed(), intentionPurchaseSaleSummarizedList );
    }

     @Override
     public IntentionPurchaseSaleVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
         List<IntentionPurchaseSale> intentionPurchaseSaleList = intentionPurchaseSalePersistence.findOperationBetweenDates(email, startDate, endDate, StatusType.FINISHED);
         List<IntentionPurchaseSale> intentionPurchaseSaleResultList = new ArrayList<>();
         Map<String, BigDecimal> cryptoQuotationCache = new HashMap<>();
         BigDecimal purchaseQuotation = cryptoCoinService.getThePriceOfThePurchaseDollar();
         BigDecimal sellQuotation = cryptoCoinService.getThePriceOfTheSellDollar();
         BigDecimal totalDollarAmount = BigDecimal.ZERO;
         BigDecimal totalPesosAmount = BigDecimal.ZERO;

         for(IntentionPurchaseSale intentionPurchaseSale : intentionPurchaseSaleList) {
             totalDollarAmount = totalDollarAmount.add(intentionPurchaseSale.getQuotationBase());
             totalPesosAmount = totalPesosAmount.add(intentionPurchaseSale.getPesosAmount());
             String cryptoCoinName = intentionPurchaseSale.getCryptoCoinName();
             BigDecimal pesosAmount;

             if(!cryptoQuotationCache.containsKey(cryptoCoinName)) {
                 cryptoQuotationCache.put(cryptoCoinName, cryptoCoinService.getExternalQuotationByName(cryptoCoinName));
             }

             BigDecimal actuallyQuotationBase = cryptoQuotationCache.get(cryptoCoinName);

             if(intentionPurchaseSale.getIntentionType() == IntentionType.PURCHASE) {
                 pesosAmount = actuallyQuotationBase.multiply(purchaseQuotation);
             } else {
                 pesosAmount = actuallyQuotationBase.multiply(sellQuotation);
             }

             intentionPurchaseSale.setQuotationBase(actuallyQuotationBase);
             intentionPurchaseSale.setPesosAmount(pesosAmount);
             intentionPurchaseSaleResultList.add(intentionPurchaseSale);
         }

         return new IntentionPurchaseSaleVolumeInfo(totalDollarAmount, totalPesosAmount, intentionPurchaseSaleResultList);
     }
 }
