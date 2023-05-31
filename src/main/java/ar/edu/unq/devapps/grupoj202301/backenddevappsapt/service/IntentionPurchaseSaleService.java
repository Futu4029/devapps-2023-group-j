package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSale;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleCoreData;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleUserInfo;
import ar.edu.unq.devapps.grupoj202301.backenddevappsapt.model.IntentionPurchaseSale.IntentionPurchaseSaleVolumeInfo;

import java.io.IOException;
import java.time.LocalDateTime;

public interface IntentionPurchaseSaleService extends GenericService<IntentionPurchaseSale> {
    IntentionPurchaseSale createIntentionPurchaseSale(IntentionPurchaseSaleCoreData intentionPurchaseSaleInitialData);
    IntentionPurchaseSaleUserInfo getActivesTransactions(String email) throws IOException;
    IntentionPurchaseSaleVolumeInfo volumeOperatedBetweenDates(String email, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
}
