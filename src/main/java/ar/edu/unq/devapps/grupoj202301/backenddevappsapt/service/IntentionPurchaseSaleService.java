package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleCoreData;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleUserInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleVolumeInfo;

import java.io.IOException;
import java.time.LocalDateTime;

public interface IntentionPurchaseSaleService extends GenericService<IntentionPurchaseSale> {
    IntentionPurchaseSale create(IntentionPurchaseSaleCoreData intentionPurchaseSaleInitialData);
    String cancel(String intentionID, String email);
    String proceed(String intentionID, String email);
    String confirm(String intentionID, String email);
    IntentionPurchaseSaleUserInfo getActivesTransactions(String email) throws IOException;
    IntentionPurchaseSaleVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
}
